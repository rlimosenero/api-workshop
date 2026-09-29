package com.api.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.application.configuration.properties.OriginsConfigProperties;
import com.api.application.service.ws.HelloWorldServiceRestService;
import com.api.application.service.ws.impl.HelloWorldServiceRestServiceImpl;
import com.bpi.framework.web.configurer.client.RestApiConfigurerComponent;

@Configuration
public class RestTemplateConfig {

    @Bean
    public HelloWorldServiceRestService helloWorldServiceRestService(RestApiConfigurerComponent configurer,
                                                                     OriginsConfigProperties helloWorldServiceProperties) {
        return configurer.configure(helloWorldServiceProperties, HelloWorldServiceRestServiceImpl.class);
    }
}
