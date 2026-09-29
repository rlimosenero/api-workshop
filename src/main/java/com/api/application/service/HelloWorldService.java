package com.api.application.service;

import org.springframework.http.HttpHeaders;
import com.bpi.framework.web.component.RestResponseContentWrapper;
import com.fasterxml.jackson.databind.JsonNode;

public interface HelloWorldService {
    RestResponseContentWrapper<HttpHeaders, JsonNode> getHelloWorld();
}
