package com.example.demo;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class ServiceAutoConfiguration {

  @Configuration
  //used if a property is explicitly set
  static class NoopConfiguration {

    @Bean
    @ConditionalOnProperty(name = "a.b.c", havingValue = "true")
    public ConfigHelper configHelper() {
      return new ConfigHelper("NOOP");
    }
  }

  @Configuration
  //used if a Undertow is present
  @AutoConfigureAfter(NoopConfiguration.class)
  @ConditionalOnClass(name = "io.undertow.Undertow")
  static class UndertowConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = ConfigHelper.class)
    public ConfigHelper configHelper() {
      return new ConfigHelper("Option 1");
    }
  }


  @Configuration
  //used if a Undertow is absent
  @AutoConfigureAfter(NoopConfiguration.class)
  @ConditionalOnMissingClass(value = "io.undertow.Undertow")
  static class DefaultConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = ConfigHelper.class)
    public ConfigHelper configHelper() {
      return new ConfigHelper("Default");
    }
  }

  static class ConfigHelper {

    public ConfigHelper(String val) {
      System.out.println("Value= "+ val);
    }
  }

}
