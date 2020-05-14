package com.andy.controller;

import com.andy.controller.response.ErrorResponse;
import com.andy.exception.NotFoundException;
import com.andy.exception.ZuoraServiceException;
import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ZuoraServiceException.class)
    public ResponseEntity<ErrorResponse> handleZuoraService(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        ErrorResponse errorResponse = new Gson().fromJson(e.getMessage(), ErrorResponse.class);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleDefault(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
