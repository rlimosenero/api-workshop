package com.api.application.controller.apps;

import com.api.application.service.HelloWorldService;
import com.bpi.framework.web.component.ResponseConverter;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bpi.framework.security.policies.EnableAccessPolicies;
import com.bpi.framework.web.model.Response;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/application/apps")
@EnableAccessPolicies
@RequiredArgsConstructor
public class HelloWorldController implements HelloWorldApi {

    private final HelloWorldService helloWorldService;

    @Override
    @GetMapping("/hello")
    public ResponseEntity<Response<JsonNode>> getHello(@Parameter(description = "The apiKey used to authenticate access", required = true) @RequestHeader String apiKey,
        @Parameter(description = "The api secret used to authenticate access", required = true) @RequestHeader String apiSecret,
        @Parameter(description = "Request Unique ID", required = true) @RequestHeader String requestUID,
        @Parameter(description = "Resource Owner ID - Online or Mobile", required = true) @RequestHeader String resourceOwnerId) {

        return ResponseConverter.convert(helloWorldService.getHelloWorld());
    }
}
