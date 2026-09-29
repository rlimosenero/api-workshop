package com.api.application.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.bpi.framework.web.configproperties.HttpApiConfigProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Validated
@Component
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("bpi.rest.origins")
public class OriginsConfigProperties extends HttpApiConfigProperties {

    private String authorization;

    @NotBlank
    private String helloWorldServiceEndpoint;
}
