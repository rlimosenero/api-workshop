package com.api.application.service.biz.impl;

import org.springframework.stereotype.Service;

import com.api.application.exception.PassThroughRuntimeException;
import com.api.application.service.biz.HelloWorldServiceBiz;
import com.api.application.service.ws.HelloWorldServiceRestService;
import com.bpi.framework.web.exception.HostRuntimeException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloWorldServiceBizImpl implements HelloWorldServiceBiz {

    private final HelloWorldServiceRestService helloWorldServiceRestService;

    @Override
    public JsonNode getHelloWorld() {
        try {
            return helloWorldServiceRestService.executeGetHelloWorld();
        } catch (HostRuntimeException ex) {
            log.error("HostRunTimeException occurred after completing hello world service", ex);
            throw new PassThroughRuntimeException(ex.getCode(), ex.getMessage(), ex.getHttpStatus(), ex);
        }
    }
}
