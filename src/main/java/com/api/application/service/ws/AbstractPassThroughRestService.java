package com.api.application.service.ws;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.bpi.framework.web.component.client.AbstractErrorAwareRestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPassThroughRestService extends AbstractErrorAwareRestTemplate {

    protected AbstractPassThroughRestService(DefaultError defaultError) {
        super(defaultError);
    }

    public JsonNode executePost(JsonNode request, String authorization, String path) {
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(request, constructAuthorizationHeader(authorization));

        ResponseEntity<JsonNode> responseEntity = post(path, requestEntity, JsonNode.class);
        log.debug("Origins Response: " + responseEntity);

        return responseEntity.getBody();
    }

    public JsonNode executeGet(String authorization, String path) {
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(constructAuthorizationHeader(authorization));

        ResponseEntity<JsonNode> responseEntity = get(path, requestEntity, JsonNode.class);
        log.debug("Origins Response: " + responseEntity);

        return responseEntity.getBody();
    }

    protected static HttpHeaders constructAuthorizationHeader(String authorization) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authorization);
        log.debug("Auth: " + httpHeaders.getFirst(HttpHeaders.AUTHORIZATION));
        return httpHeaders;
    }
}
