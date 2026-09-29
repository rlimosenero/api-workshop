package com.api.application.configuration;

import com.api.application.errorcode.ServiceOrderErrorCode;
import com.api.application.errormapping.ServiceOrderErrorWrapping;
import com.bpi.framework.commons.errorcode.ErrorCode;
import com.bpi.framework.web.autoconfigure.ErrorHandlerAutoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ErrorHandlerConfig {

    @Bean
    public ErrorCode internalErrorCode() { return ServiceOrderErrorCode.INTERNAL_ERROR; }

    @Bean
    public ErrorCode serviceErrorCode() {
        return ServiceOrderErrorCode.SERVICE_ERROR;
    }

    @Bean
    public ErrorHandlerAutoConfig.ErrorCodeLookup securityErrorCodeLookup() {
        return new SecurityErrorCodeLookupImpl();
    }

    private static class SecurityErrorCodeLookupImpl extends ErrorHandlerAutoConfig.ErrorCodeLookup {

        @Override
        public ErrorCode parse(String code) {
            log.info("ErrorHandlerConfig parse error {}", code);
            return ServiceOrderErrorWrapping.lookupByCode(code);
        }
    }
}
