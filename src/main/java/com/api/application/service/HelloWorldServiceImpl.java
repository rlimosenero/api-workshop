package com.api.application.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.api.application.service.biz.HelloWorldServiceBiz;
import com.bpi.framework.web.component.RestResponseContentWrapper;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloWorldServiceImpl implements HelloWorldService {

    private final HelloWorldServiceBiz helloWorldServiceBiz;

    @Override
    public RestResponseContentWrapper<HttpHeaders, JsonNode> getHelloWorld() {
        return new RestResponseContentWrapper<>(helloWorldServiceBiz.getHelloWorld());
    }
}
