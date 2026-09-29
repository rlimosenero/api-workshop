package com.api.application.controller.apps;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.bpi.framework.web.model.Response;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestHeader;

public interface HelloWorldApi {

    @Operation(
        description = "Simple Hello World API",
        tags = { "Hello World" }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
            content = {@Content(schema = @Schema(implementation = JsonNode.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request",
            content = {@Content(schema = @Schema(implementation = Response.class))}),
        @ApiResponse(responseCode = "500", description = "Service error occurred",
            content = {@Content(schema = @Schema(implementation = Response.class))})
    })
    ResponseEntity<Response<JsonNode>> getHello(
        @Parameter(description = "The apiKey used to authenticate access", required = true) @RequestHeader String apiKey,
        @Parameter(description = "The api secret used to authenticate access", required = true) @RequestHeader String apiSecret,
        @Parameter(description = "Request Unique ID", required = true) @RequestHeader String requestUID,
        @Parameter(description = "Resource Owner ID - Online or Mobile", required = true) @RequestHeader String resourceOwnerId
    );
}
