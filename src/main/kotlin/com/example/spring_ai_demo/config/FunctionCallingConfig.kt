package com.example.spring_ai_demo.config

import com.example.spring_ai_demo.service.MockWeatherService
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.model.function.FunctionCallbackWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FunctionCallingConfig {
    @Bean
    fun weatherFunctionInfo(): FunctionCallback = FunctionCallbackWrapper.builder(MockWeatherService())
        .withName("CurrentWeather")
        .withDescription("Get the weather in location")
        .build()
}