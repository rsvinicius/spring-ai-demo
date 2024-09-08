package com.example.spring_ai_demo.controller

import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.document.Document
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.stream.Collectors

@RestController
class ChatController(
    // Using spring boot autoconfiguration, see official spring AI documentation for further customization (temperature, model, etc...)
    private val chatModel: OllamaChatModel,
    private val vectorStore: SimpleVectorStore
) {
    // Chat
    @GetMapping("/ai/generate")
    fun generate(
        @RequestParam(
            value = "message",
            defaultValue = "How can you help me?"
        ) message: String?
    ): String? {

        val prompt = Prompt(UserMessage(message))

        return chatModel.call(prompt).result.output.content
    }

    // Chat with function calling
    @GetMapping("/ai/generateFunction")
    fun generateWithFunction(
        @RequestParam(
            value = "message",
            defaultValue = "What's the weather like in Tokyo?"
        ) message: String?
    ): String? {

        val prompt = Prompt(UserMessage(message), OllamaOptions.builder().withFunction("CurrentWeather").build())

        return chatModel.call(prompt).result.output.content
    }

    // Streaming chat (function calling not supported at moment with streaming for ollama api)
    @GetMapping("/ai/generateStream")
    fun generateStream(
        @RequestParam(
            value = "message",
            defaultValue = "Tell me a joke"
        ) message: String?
    ): Flux<ChatResponse> {
        val prompt = Prompt(UserMessage(message))
        return chatModel.stream(prompt)
    }

    // Add embedding info
    @PostMapping("/ai/embeddings/add")
    fun embed(@RequestBody docs: List<String>) {
        val documents = docs.map { Document(it) }

        vectorStore.add(documents)
    }

    // Similarity search
    @GetMapping("/ai/embeddings/search")
    fun search(@RequestParam(value = "query") query: String): String? {
        val similarDocuments = vectorStore.similaritySearch(
            SearchRequest.defaults()
                .withQuery(query)
                .withSimilarityThreshold(0.5)
        )

        return similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()))
    }
}