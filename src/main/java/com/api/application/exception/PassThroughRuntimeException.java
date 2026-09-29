package com.api.application.exception;

import java.io.Serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class PassThroughRuntimeException extends RuntimeException {

    private final String code;

    private final String message;
    
    private final transient JsonNode errorData;

    private final HttpStatus httpStatus;

    @Serial
    private static final long serialVersionUID = 1L;

    public PassThroughRuntimeException(String code, String message, HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorData = extractJsonErrorData(cause);
    }

    private JsonNode extractJsonErrorData(Throwable cause) {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStringMessage = ((HttpStatusCodeException)(cause.getCause())).getResponseBodyAsString();
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(jsonStringMessage);
        } catch (JsonProcessingException e) {
            log.error("Fetching error response body from Origins API failed", e);
        }

        return jsonNode;
    }
}
