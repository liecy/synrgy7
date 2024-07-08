package com.rahmi.binfood.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static ResponseEntity<Object> success(HttpStatus status, String message, Object data, String dataKey) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);

        Map<String, Object> dataWrapper = new HashMap<>();
        dataWrapper.put(dataKey, data);
        response.put("data", dataWrapper);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> success(HttpStatus status, String message, Object data) {
        return success(status, message, data, "data");
    }

    public static ResponseEntity<Object> error(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}