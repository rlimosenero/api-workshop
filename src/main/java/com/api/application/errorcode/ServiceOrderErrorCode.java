package com.api.application.errorcode;

import com.bpi.framework.commons.errorcode.ErrorCode;


public class ServiceOrderErrorCode extends ErrorCode {

    private static final String DOMAIN_CODE = "SOL";

    public static final ErrorCode SECURITY_ERROR_INVALID_SECRET = security("001", "Invalid Credentials");
    public static final ErrorCode SECURITY_ERROR_INVALID_KEY = security("002", "Not Authorized");

    public static final ErrorCode BUSINESS_ERROR = business("999", "Business Error");
    public static final ErrorCode CREATE_ORIGINS_BAD_REQUEST = business("001", "Bad Request in Create FS Web service");
    public static final ErrorCode COMPLETE_ORIGINS_BAD_REQUEST = business("002", "Bad Request in Complete FS Web service");

    public static final ErrorCode INTERNAL_ERROR = internal("999", "Internal Error");

    public static final ErrorCode SERVICE_ERROR = service("999", "Back end service error");
    public static final ErrorCode HELLO_WORLD_SERVICE_DOWN_ERROR = service("002", "There is a problem connecting to Hello World Web service");
    public static final ErrorCode HELLO_WORLD_SERVICE_ERROR = service("008", "There is an access error in Hello World Web service");
    
    private ServiceOrderErrorCode(String code, String message) {
        super(code, message);
    }

    private static ErrorCode service(String numericCode, String message) {
        String code = String.format("%sSE%s", DOMAIN_CODE, numericCode);
        return new ServiceOrderErrorCode(code, message);
    }

    private static ErrorCode internal(String numericCode, String message) {
        String code = String.format("%sIE%s", DOMAIN_CODE, numericCode);
        return new ServiceOrderErrorCode(code, message);
    }

    private static ErrorCode business(String numericCode, String message) {
        String code = String.format("%sBE%s", DOMAIN_CODE, numericCode);
        return new ServiceOrderErrorCode(code, message);
    }

    private static ErrorCode security(String numericCode, String message) {
        String code = String.format("%sSC%s", DOMAIN_CODE, numericCode);
        return new ServiceOrderErrorCode(code, message);
    }
}
