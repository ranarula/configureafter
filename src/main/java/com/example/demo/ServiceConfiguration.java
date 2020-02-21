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
public class ServiceConfiguration {

  @Configuration
  //used if a property is explicitly set
  @ConditionalOnProperty(name = "a.b.c", havingValue = "true")
  static class NoopConfiguration {

    @Bean
    public ConfigHelper configHelper() {
      return new ConfigHelper("NOOP");
    }
  }

  @Configuration
  //used if a Undertow is present
  @ConditionalOnClass(name = "io.undertow.Undertow")
  @ConditionalOnMissingBean(value = ConfigHelper.class)
  static class UndertowConfiguration {

    @Bean
    public ConfigHelper configHelper() {
      return new ConfigHelper("Option 1");
    }
  }


  @Configuration
  //used if a Undertow is absent
  @AutoConfigureAfter(NoopConfiguration.class)
  @ConditionalOnMissingClass(value = "io.undertow.Undertow")
  @ConditionalOnMissingBean(value = ConfigHelper.class)
  static class DefaultConfiguration {

    @Bean
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
