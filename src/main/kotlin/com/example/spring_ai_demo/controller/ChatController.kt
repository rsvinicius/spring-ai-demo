package com.example.spring_ai_demo.controller

import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.document.Document
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class ChatController(
    // Using spring boot autoconfiguration, see official spring AI documentation for further customization (temperature, model, etc...)
    private val chatModel: OllamaChatModel,
    private val vectorStore: SimpleVectorStore
) {
    // Chat
    @PostMapping("/chats")
    fun generate(
        @RequestBody message: String
    ): String? {

        val prompt = Prompt(UserMessage(message))

        return chatModel.call(prompt).result.output.content
    }

    // Chat with function calling
    @PostMapping("/chats/functions")
    fun generateWithFunction(
        @RequestBody message: String
    ): String? {

        val prompt = Prompt(UserMessage(message), OllamaOptions.builder().withFunction("CurrentWeather").build())

        return chatModel.call(prompt).result.output.content
    }

    // Streaming chat (function calling not supported at moment with streaming for ollama api)
    @PostMapping("/chats/stream")
    fun generateStream(
        @RequestBody message: String
    ): Flux<ChatResponse> {
        val prompt = Prompt(UserMessage(message))
        return chatModel.stream(prompt)
    }

    // Add embedding info
    @PostMapping("/embeddings")
    fun embed(@RequestBody docs: List<String>) {
        val documents = docs.map { Document(it) }

        vectorStore.add(documents)
    }

    // Similarity search
    @GetMapping("/embeddings/search")
    fun search(@RequestParam(value = "text") text: String): String? {
        val similarDocuments = vectorStore.similaritySearch(
            SearchRequest.defaults()
                .withQuery(text)
                .withSimilarityThreshold(0.5)
        )

        return similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()))
    }
}