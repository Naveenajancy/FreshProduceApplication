package com.freshproduceapp.exception;

public class RequiredQuantityNotAvailableException extends RuntimeException {
    public RequiredQuantityNotAvailableException(String name, Double requested, Double available){
        super("Produce " + name + " not available for " + requested + " pounds, only " + available + " lbs is available");
    }
}
