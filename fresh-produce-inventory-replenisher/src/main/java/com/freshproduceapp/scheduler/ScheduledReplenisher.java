package com.freshproduceapp.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshproduceapp.dao.FreshProduceInventoryDao;
import com.freshproduceapp.model.FreshProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduledReplenisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledReplenisher.class);
//      ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs (Plain Old Java Objects),
//      or to and from a general-purpose JSON Tree Model (JsonNode), as well as related functionality for performing conversions.

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private FreshProduceInventoryDao freshProduceInventoryDao;


    @Value("${aws.queue.url}")
    private String queueUrl;

    @Scheduled(initialDelay = 30L, fixedDelay = 60L, timeUnit = TimeUnit.SECONDS)
    public void scheduleReplenish() {
//        Default constructor for ReceiveMessageRequest object.
//        The URL of the Amazon SQS queue from which messages are received.
//        Params queueUrl – The URL of the Amazon SQS queue from which messages are received.
//        Queue URLs and names are case-sensitive.
//                Returns:  Returns a reference to this object so that method calls can be chained together.
//         Receive messages from the queue
        LOGGER.info(" ********* Invoking schedule method *********");
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        for (Message message : messages) {
            FreshProduce freshProduce = readJavaObjectFromJson(message.body());
            LOGGER.info("Fresh Produce to be replenished: {} ", freshProduce);

            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle()).build();

            //update the fresh produce item in the SQS queue
            freshProduceInventoryDao.updateFreshProduce(freshProduce.getFreshProduceId(), 300.00);

            //We need to delete the message in the SQS queue
            //Unless we delete the message we will read them every time.

            sqsClient.deleteMessage(deleteMessageRequest);
            LOGGER.info("Fresh Produce Successfully Replenished {} ", freshProduce);
        }
    }

    //Jackson Library is used to convert the JSON String into Java Object
    private FreshProduce readJavaObjectFromJson(String jsonMessage) {
        try {
            return objectMapper.readValue(jsonMessage, FreshProduce.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
