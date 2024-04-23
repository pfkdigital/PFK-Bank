package org.techtest.starling.controller.advise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.techtest.starling.exception.AccountNotFoundException;
import org.techtest.starling.exception.ApiRuntimeException;
import org.techtest.starling.exception.InsufficientFundsException;
import org.techtest.starling.exception.SavingGoalNotFoundException;
import org.techtest.starling.model.ErrorDetail;
import org.techtest.starling.model.ErrorResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerAdvise {

    // This method is called when a StarlingApiRuntimeException is thrown
    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleStarlingApiRuntimeException(ApiRuntimeException e) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage());
        ErrorDetail responseErrorDetail = new ErrorDetail(e.getResponse().getStatusText());

        errorResponse.setErrors(List.of(errorDetail, responseErrorDetail));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This method is called when an AccountNotFoundException is thrown
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        errorResponse.setErrors(Collections.singletonList(new ErrorDetail(e.getMessage())));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // This method is called when an InsufficientFundsException is thrown
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        errorResponse.setErrors(Collections.singletonList(new ErrorDetail(e.getMessage())));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // This method is called when a SavingGoalNotFoundException is thrown
    @ExceptionHandler(SavingGoalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSavingGoalNotFoundException(SavingGoalNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        errorResponse.setErrors(Collections.singletonList(new ErrorDetail(e.getMessage())));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
