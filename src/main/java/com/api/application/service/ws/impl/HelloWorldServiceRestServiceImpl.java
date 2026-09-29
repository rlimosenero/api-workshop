package com.api.application.service.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.api.application.configuration.properties.OriginsConfigProperties;
import com.api.application.errorcode.ServiceOrderErrorCode;
import com.api.application.service.ws.AbstractPassThroughRestService;
import com.api.application.service.ws.HelloWorldServiceRestService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldServiceRestServiceImpl extends AbstractPassThroughRestService implements
                                                                                     HelloWorldServiceRestService {
    private OriginsConfigProperties originsConfigProperties;

    private static final DefaultError REST_PROPERTIES = new DefaultError(
        ServiceOrderErrorCode.HELLO_WORLD_SERVICE_DOWN_ERROR,
            ServiceOrderErrorCode.HELLO_WORLD_SERVICE_ERROR);

    public HelloWorldServiceRestServiceImpl() {
        super(REST_PROPERTIES);
    }

    @Autowired
    public void setOriginsServicingOrderLoanProperties(OriginsConfigProperties originsConfigProperties) {
        this.originsConfigProperties = originsConfigProperties;
    }

    @Override
    public JsonNode executeGetHelloWorld() {
        log.info("Get Hello World");
        return executeGet(originsConfigProperties.getAuthorization(),
                originsConfigProperties.getHelloWorldServiceEndpoint());
    }
}
