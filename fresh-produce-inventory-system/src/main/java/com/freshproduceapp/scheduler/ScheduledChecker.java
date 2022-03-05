package com.freshproduceapp.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshproduceapp.model.FreshProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.concurrent.TimeUnit;

@Service
public class ScheduledChecker {
    //The org.slf4j.Logger interface is the main user entry point of SLF4J API. It is expected that logging takes place through concrete implementations of this interface.
    //Note that logging statements can be parameterized in presence of an exception/throwable.

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledChecker.class);


    /**
     * {@link ObjectMapper} is from the Jackson Library. Its used to convert JAVA to JSON & Vice Versa
     * NOTE: Spring Framework uses Jackson Library to do the json to java conversion and vice versa
     * Making it a static final object as it is a costly object to create and multiple copies should not be created
     * Note that copy operation is as expensive as constructing a new ObjectMapper instance:
     * if possible, you should still pool and reuse mappers if you intend to use them for multiple operations.
     */

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private SqsClient sqsClient;

    @Value("${aws.queue.url}")
    private String queueUrl;
    @Value("${api.host.baseurl}")
    private String apiHost;

    //Schedule method starts after 30 seconds
    //Runs every 10 seconds after that

   // @Scheduled(initialDelay = 30L, fixedDelay = 60 * 60 * 60L, timeUnit = TimeUnit.SECONDS)
    @Scheduled(initialDelay = 30L, fixedDelay = 60L, timeUnit = TimeUnit.SECONDS)
    public void scheduledMethod() {
        FreshProduce[] freshProduces = restTemplate.getForObject(apiHost + "/fresh/produce/mart/unfilteredproducts", FreshProduce[].class);
        for (FreshProduce freshProduce : freshProduces) {
            //If any produce becomes less than 50 lbs request to restock

            if (freshProduce.getAvailablePounds() < 50) {
                LOGGER.info("Low Produce Quantity Detected {}", freshProduce);

                String message = getFreshProduceAsJson(freshProduce);
                SendMessageRequest sendMessageRequest = SendMessageRequest.builder() //
                        .queueUrl(queueUrl) //The URL of the Amazon SQS queue to which a message is sent.
                        .messageBody(message) //The message to send. The minimum size is one character. The maximum size is 256 KB.
                        .build(); // An immutable object that is created from the properties that have been set on the builder. Returns: an instance of T


                //Delivers a message to the specified queue. A message can include only XML, JSON, and unformatted text
                //Sending message to SQS Initiating the Replenishment
                sqsClient.sendMessage(sendMessageRequest);
            }
        }
    }


        private String getFreshProduceAsJson(FreshProduce freshProduce)  {
            try {
                return OBJECT_MAPPER.writeValueAsString(freshProduce);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e); // Runtime exceptions shouldn't be caught since it can't be reasonably expected to recover from them or handle them
            }

        }
}







