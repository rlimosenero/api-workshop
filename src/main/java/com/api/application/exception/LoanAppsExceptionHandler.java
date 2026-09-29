package com.api.application.exception;

import java.util.List;
import java.util.Map;

import com.api.application.controller.apps.*;
import com.api.application.errormapping.ServiceOrderErrorWrapping;
import com.bpi.framework.web.exception.HostConnectionException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.application.errorcode.ServiceOrderErrorCode;
import com.bpi.framework.commons.errorcode.ErrorCode;
import com.bpi.framework.web.exceptionhandler.DefaultErrorAwareExceptionHandler;
import com.bpi.framework.web.model.ErrorDetail;
import com.bpi.framework.web.model.Response;
import com.bpi.framework.web.model.ResponseStatus;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = { HelloWorldController.class})
public class LoanAppsExceptionHandler extends DefaultErrorAwareExceptionHandler {

        @ExceptionHandler
        public final ResponseEntity<Object> handlePassThroughRuntimeException(PassThroughRuntimeException ex) {

                Response<Void> response = new Response<>();
                response.setStatus(ResponseStatus.ERROR);
                response.setCode(ServiceOrderErrorCode.SERVICE_ERROR.getCode());
                response.setDescription(ServiceOrderErrorCode.SERVICE_ERROR.getMessage());

                PassThroughErrorDetail passThroughErrorDetail = new PassThroughErrorDetail(ex.getCode(), ex.getMessage(), ex.getErrorData());
                HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

                if (HttpStatus.BAD_REQUEST == ex.getHttpStatus()) {
                        ErrorCode errorCode = ServiceOrderErrorWrapping.lookupByServiceErrorCode(ex.getCode());
                        response.setStatus(ResponseStatus.ERROR);
                        response.setCode(ServiceOrderErrorCode.BUSINESS_ERROR.getCode());
                        response.setDescription(ServiceOrderErrorCode.BUSINESS_ERROR.getMessage());
                        passThroughErrorDetail = new PassThroughErrorDetail(errorCode.getCode(), errorCode.getMessage(), ex.getErrorData());
                        defaultHttpStatus = HttpStatus.BAD_REQUEST;
                }

                response.setErrorDetails(List.of(passThroughErrorDetail));

                log.error("Returning error code '{}' with description '{}' to caller", response.getCode(), response.getDescription());
                return new ResponseEntity<>(response, defaultHttpStatus);
        }

        @ExceptionHandler
        public final ResponseEntity<Object> handleHostConnectionException(HostConnectionException ex) {

                Response<Void> response = new Response<>();
                response.setStatus(ResponseStatus.ERROR);
                response.setCode(ServiceOrderErrorCode.SERVICE_ERROR.getCode());
                response.setDescription(ServiceOrderErrorCode.SERVICE_ERROR.getMessage());
                response.setErrorDetails(List.of(new PassThroughErrorDetail(ex.getCode(), ex.getMessage(), null)));

                log.error("Returning error code '{}' with description '{}' to caller", response.getCode(), response.getDescription());
                return new ResponseEntity<>(response, ex.getHttpStatus());
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class PassThroughErrorDetail extends ErrorDetail {

                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonUnwrapped(prefix = "errorData")
                private JsonNode errorData;

                private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

                @JsonAnyGetter
                public Map<String, Object> errorData() {
                        return OBJECT_MAPPER.convertValue(errorData, new TypeReference<>() {
                                }
                        );
                }

                public PassThroughErrorDetail(String errorCode, String errorMessage, JsonNode errorData) {
                        super(errorCode, errorMessage);
                        this.errorData = errorData;
                }
        }
}
