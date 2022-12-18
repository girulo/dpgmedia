package com.example.dpgmedia.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.Objects.isNull


@Configuration
class AppConfig {

    @Value("\${demo.base.url}")
    private val demoBaseUrl: String? = null

    //RestTemplate modified with a base url to call only GOT API. Not the best for a normal service, but perfect for this assignment
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        if (isNull(demoBaseUrl)) {
            throw BeanInitializationException("Please define the property demo.base.url, otherwise the app can not initialized");
        }
        return builder.rootUri(demoBaseUrl)
            .setConnectTimeout(Duration.ofSeconds(10))
            .build();
    }
    //Changed JSON Strategy to adapt to the GOT API data objects
    @Bean fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder? {
        return Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
    }
}