package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@Import({
    ServiceConfiguration.NoopConfiguration.class,
    ServiceConfiguration.UndertowConfiguration.class,
    ServiceConfiguration.DefaultConfiguration.class
})
public class ServiceAutoConfiguration {

}
