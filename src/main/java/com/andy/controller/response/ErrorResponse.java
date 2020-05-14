package com.andy.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String success;
    private String processId;
    private List<Reason> reasons;

    @Data
    private static class Reason {
        private String code;
        private String message;
    }

}
