# Spring AI Demo

This project is a Spring Boot application that demonstrates an REST API using Ollama AI. It features embedding vectors, function calling, and streaming capabilities. The API provides endpoints for generating responses, interacting with functions, and performing similarity searches using embeddings.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
    - [Generate Chat](#generate-chat)
    - [Generate Chat with Function Calling](#generate-chat-with-function-calling)
    - [Streaming Chat](#streaming-chat)
    - [Add Embedding Info](#add-embedding-info)
    - [Similarity Search](#similarity-search)
- [Embedding Vectors](#embedding-vectors)
- [Ollama AI Integration](#ollama-ai-integration)

## Overview

This project demonstrates a simple AI-based API using [Ollama AI](https://ollama.ai/), showcasing the following features:

- **Chat generation**: Generate conversational responses.
- **Function calling**: Invoke predefined functions during AI conversations.
- **Streaming chat**: Real-time chat responses (currently without function support).
- **Embedding vectors**: Add and search embeddings for similarity analysis.

By default:
- The **Mistral model** is used for conversational AI.
- The **nomadic-embeded-text** model is required for embedding functionalities.


## Prerequisites

Before running the project, ensure you have the following:

- **Java 21** or newer
- **Gradle**
- **Ollama AI** installed locally on your machine 
- **Default models**:
  - chat: `ollama pull mistral`
  - embedding: `ollama pull nomic-embed-text`

> **Note**: Ollama AI does not require API credentials but needs to be set up locally. Follow the installation guide on the [Ollama AI website](https://ollama.ai/) to install it on your system.

## Getting Started

1. **Clone the repository**:

   ```bash
   git clone https://github.com/rsvinicius/spring-ai-demo.git
   cd spring-ai-demo
   ```

2. **Configure the project**:

Ensure that Ollama AI is installed and running locally on your machine. Update the application.yaml file to modify the AI model or any other configuration if needed.

3. **Build and run the project:**:

   ```bash
   ./gradlew clean build
   ./gradlew bootRun
   ```
   
4. **The application will be running on http://localhost:9080.**

## API Endpoints

### Generate Chat

Generate a conversational AI response.

- **Endpoint**: `POST /api/chats`

- **Description**: Generates a response based on the given input.

- **Request Body**:
    ```json
      { "message": "Hello" }
  ```

- **Example**:
    ```bash
    curl -X POST 'http://localhost:9080/api/chats' \
  -H 'Content-Type: application/json' \
  -d '{"message": "Hello"}'
    ```

### Generate Chat with Function Calling

Generate a conversational response and utilize function calling.

- **Endpoint**: `POST /api/chats/functions`

- **Description**: Generates a response and calls functions if needed.

- **Request Body**:
    ```json
      { "message": "What is the temperature like in New York?" }
  ```

- **Example**:
    ```bash
  curl -X POST 'http://localhost:9080/api/chats/functions' \
  -H 'Content-Type: application/json' \
  -d '{"message": "What is the temperature like in New York?"}'
    ```

### Streaming Chat

Generate a streaming conversational AI response.

- **Endpoint**: `POST /api/chats/stream`

- **Description**: Generates a real-time streaming response. Note that function calling is currently not supported in this mode.

- **Request Body**:
    ```json
      { "message": "Start a streaming chat" }
    ```

- **Example**:
    ```bash
  curl -X POST 'http://localhost:9080/api/chats/stream' \
  -H 'Content-Type: application/json' \
  -d '{"message": "Start a streaming chat"}'
    ```

### Add Embedding Info

Add new embedding data to the system.

- **Endpoint**: `POST /api/embeddings`

- **Description**: Adds new embedding vectors based on the provided list of documents.

  - **Request Body**:
      ```json
        [
          "Document content 1",
          "Document content 2"
        ]
    ```
    
- **Example**:
    ```bash
    curl -X POST 'http://localhost:9080/api/embeddings' \
  -H 'Content-Type: application/json' \
  -d '[
        "Document content 1",
        "Document content 2"
      ]'
    ```

### Similarity Search

Search for similar embeddings.

- **Endpoint**: `GET /api/embeddings/search`

- **Description**: Performs a similarity search based on the provided query.

- **Query Parameters**:
    - `text`: The input text to find similar embeddings.

- **Example**:
    ```bash
    curl -X GET 'http://localhost:9080/api/embeddings/search?text=Document'
    ```
  
## Embedding Vectors

Embedding vectors are used for similarity analysis in AI applications. This project allows you to add and search embedding data, facilitating advanced search and recommendation functionalities.

## Model Configuration

By default:
- The Mistral model is used for conversational AI.
- The nomadic-embeded-text model is required for embedding functionalities.

You can change the model by editing the `application.yaml` file:
```yaml
ollama:
  chat:
    options:
      model: mistral
  embedding:
    options:
      model: nomic-embed-text
```  

For a list of available models, refer to the [Ollama AI documentation](https://ollama.com/).

## Ollama AI Integration

The project integrates with Ollama AI, which must be installed locally. Ensure that Ollama AI is set up and configured properly on your machine before running the application.
