package org.techtest.starling.exception;

public class SavingGoalNotFoundException extends RuntimeException {
    public SavingGoalNotFoundException(String message) {
        super(message);
    }

    public SavingGoalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
