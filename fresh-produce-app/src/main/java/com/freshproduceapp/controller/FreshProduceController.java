package com.freshproduceapp.controller;

import com.freshproduceapp.dao.FreshProduceInventoryDao;
import com.freshproduceapp.dao.TrackOrderDao;
import com.freshproduceapp.dao.TrackProductDao;
import com.freshproduceapp.exception.RequiredQuantityNotAvailableException;
import com.freshproduceapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("fresh/produce/mart")

public class FreshProduceController {
    @Autowired
    private FreshProduceInventoryDao freshProduceInventoryDao;
    @Autowired
    private TrackProductDao trackProductDao;
    @Autowired
    private TrackOrderDao trackOrderDao;



    @GetMapping("displayallproducts")
    public List<FreshProduce> getAllFreshProduce() {
        List<FreshProduce> freshProduceList = freshProduceInventoryDao.retrieveAllFreshProduceInventoryItems();
        //Filtering products if the availble pounds are <= 0.0
        List<FreshProduce> filteredFreshProduceList = freshProduceList.stream().filter(freshProduce -> freshProduce.getAvailablePounds() > 0.0).collect(Collectors.toList());
        return filteredFreshProduceList;
    }

    @GetMapping("unfilteredproducts")
    public List<FreshProduce> getAllUnfilteredProduce(){
        List<FreshProduce> unfilteredFreshProduceList = freshProduceInventoryDao.retrieveAllFreshProduceInventoryItems();
        return unfilteredFreshProduceList;
    }

    @PostMapping("completeorder")
    public com.freshproduceapp.model.CustomerReceipt completeTranscation(@RequestBody List<PurchaseOrder> purchaseOrders) {
        BigDecimal totalCost = BigDecimal.valueOf(0);
        //put the id and quantity in map and update them if all the item are available
        Map<Integer, Double> freshProduceItemQuantityMap = new HashMap<>();
        List<CustomerPurchaseItem> customerPurchaseItemList = new ArrayList<>();
        UUID uuid = UUID.randomUUID(); //generates unique id each order

        // Create trackorder Object and trackProduct Object


        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            //create trackOrder for each product
            TrackProduct trackProduct = new TrackProduct();

            FreshProduce freshProduce = freshProduceInventoryDao.selectItemById(purchaseOrder.getId());

            Double remainingQuantity = freshProduce.getAvailablePounds() - purchaseOrder.getRequiredPounds();

            trackProduct.setCustomerUUID(uuid);
            trackProduct.setFreshProduceId(freshProduce.getFreshProduceId()); //get the product Id
            trackProduct.setQuantity(purchaseOrder.getRequiredPounds());
            //check if the item available in the inventory if it's below zero throw an exception

            if (remainingQuantity < 0) {
                throw new RequiredQuantityNotAvailableException(freshProduce.getFreshProduceName(), purchaseOrder.getRequiredPounds(), freshProduce.getAvailablePounds());
            }
            freshProduceItemQuantityMap.put(freshProduce.getFreshProduceId(), remainingQuantity);

            // way to multiply BigDecimal by an Integer https://stackoverflow.com/questions/12944559/how-to-multiply-a-bigdecimal-by-an-integer-in-java
            BigDecimal cost = freshProduce.getPricePerPound().multiply(BigDecimal.valueOf(purchaseOrder.getRequiredPounds()));
            trackProduct.setPricePerPound(freshProduce.getPricePerPound());
            totalCost = totalCost.add(cost);
            trackProduct.setQuantityPrice(cost);
            //add each item to customerPurchaedList to print it in the receipt
            customerPurchaseItemList.add(setCustomerPurchasedItems(freshProduce, purchaseOrder));
            trackProductDao.createProductTrack(trackProduct);
        }
        //if everything is available update the tables and print the receipt to the user

        updateItem(freshProduceItemQuantityMap);
        com.freshproduceapp.model.CustomerReceipt customerReceipt = new com.freshproduceapp.model.CustomerReceipt();
        customerReceipt.setUuid(uuid);
        customerReceipt.setCustomerPurchaseItemList(customerPurchaseItemList);
        customerReceipt.setTotalCost(totalCost);
        customerReceipt.setDatePurchased(LocalDate.now());
        trackOrderDao.createTrackOrder(customerReceipt);


        return customerReceipt;

    }

    private void updateItem(Map<Integer, Double> freshProduceItemQuantityMap) {
        for (Integer freshProduceId : freshProduceItemQuantityMap.keySet()) {
            freshProduceInventoryDao.updateItem(freshProduceId, freshProduceItemQuantityMap.get(freshProduceId));
        }
    }

    private CustomerPurchaseItem setCustomerPurchasedItems(FreshProduce freshProduce, PurchaseOrder purchaseOrder){
        CustomerPurchaseItem customerPurchaseItem = new CustomerPurchaseItem();
        customerPurchaseItem.setName(freshProduce.getFreshProduceName());
        customerPurchaseItem.setCostPerPound(freshProduce.getPricePerPound());
        customerPurchaseItem.setPurchasedPounds(purchaseOrder.getRequiredPounds());
        return customerPurchaseItem;

    }
}
