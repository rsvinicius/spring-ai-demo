package com.example.spring_ai_demo.service

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.springframework.stereotype.Service
import java.util.function.Function

@Service
class MockWeatherService: Function<MockWeatherService.Request, MockWeatherService.Response> {
    enum class Unit { C, F }

    @JsonClassDescription("Weather API request")
    data class Request(
        @JsonProperty(required = true, value = "location")
        @JsonPropertyDescription("The city and state e.g. San Francisco, CA")
        val location: String,
        @JsonProperty(required = false, value = "unit") @JsonPropertyDescription("Temperature unit")
        val unit: Unit?
    )
    data class Response(val temp: Double, val unit: Unit)

    override fun apply(request: Request) = Response(30.0, Unit.C)
}
