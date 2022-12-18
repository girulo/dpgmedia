package com.example.dpgmedia.config

import org.springframework.beans.factory.BeanInitializationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.Objects.isNull

@Configuration
class AppConfig {

    @Value("\${demo.base.url}")
    private val demoBaseUrl: String? = null

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        if (isNull(demoBaseUrl)) {
            throw BeanInitializationException("Please define the property demo.base.url, otherwise the app can not initialized");
        }
        return builder.rootUri(demoBaseUrl)
            .setConnectTimeout(Duration.ofSeconds(10))
            .build();
    }
}