package com.api.application.errormapping;

import com.api.application.errorcode.ServiceOrderErrorCode;
import com.bpi.framework.commons.errorcode.ErrorCode;
import com.bpi.framework.web.errorcode.GeneralErrorCode;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Getter
public enum ServiceOrderErrorWrapping {

    SECURITY_ERROR_UNAUTHORIZED(GeneralErrorCode.SECURITY_ERROR_UNAUTHORIZED,ServiceOrderErrorCode.SECURITY_ERROR_INVALID_SECRET),
    SECURITY_ERROR_FORBIDDEN(GeneralErrorCode.SECURITY_ERROR_FORBIDDEN, ServiceOrderErrorCode.SECURITY_ERROR_INVALID_KEY),
    COMPLETE_ORIGINS_BAD_REQUEST(ServiceOrderErrorCode.HELLO_WORLD_SERVICE_ERROR, ServiceOrderErrorCode.COMPLETE_ORIGINS_BAD_REQUEST);

    private ErrorCode dependency;

    private ErrorCode own;

    ServiceOrderErrorWrapping(ErrorCode dependency, ErrorCode own) {
        this.dependency = dependency;
        this.own = own;
    }

    private static final Map<String, ServiceOrderErrorWrapping> codeIndex = Maps.newHashMapWithExpectedSize(ServiceOrderErrorWrapping.values().length);

    private static final Map<String, ServiceOrderErrorWrapping> serviceErrorCodeIndex = Maps.newHashMapWithExpectedSize(ServiceOrderErrorWrapping.values().length);

    static {
        for (ServiceOrderErrorWrapping e : ServiceOrderErrorWrapping.values()) {
            codeIndex.put(e.getDependency().getCode(), e);
        }
        for (ServiceOrderErrorWrapping e : ServiceOrderErrorWrapping.values()) {
            if (e.getDependency().getClass() == ServiceOrderErrorCode.class) {
                codeIndex.put(e.getDependency().getCode(), e);
            }
        }
    }

    public static ErrorCode lookupByCode(String code) {
        log.error("lookupByCode={} codeIndexSize={}", code, codeIndex.size());
        ServiceOrderErrorWrapping errorWrapping = Optional.ofNullable(codeIndex.get(code))
                .orElse(ServiceOrderErrorWrapping.SECURITY_ERROR_UNAUTHORIZED);
        return errorWrapping.getOwn();
    }

    public static ErrorCode lookupByServiceErrorCode(String code) {
        return Optional.ofNullable(codeIndex.get(code).getOwn())
                .orElse(ServiceOrderErrorCode.BUSINESS_ERROR);
    }

}
