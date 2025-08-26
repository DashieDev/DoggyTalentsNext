package doggytalents.common.lib.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import doggytalents.common.entity.Dog;

/**
 * Service to connect to a local Ollama instance for generating dog responses.
 *
 * To use this feature, you need to set up Ollama locally:
 * 1. Download and install Ollama from https://ollama.com
 *
 * 2. Pull the required model. Open your terminal and run:
 *    ollama pull gemma3:1b
 *
 * 3. Start the Ollama server on the correct host and port. The server must be
 *    running for the dog chat feature to work.
 *
 *    For Windows (PowerShell):
 *    $env:OLLAMA_HOST="127.0.0.1:11435"; ollama serve
 *
 *    For Windows (Command Prompt):
 *    set OLLAMA_HOST=127.0.0.1:11435 && ollama serve
 *
 *    For macOS/Linux (Bash):
 *    OLLAMA_HOST="127.0.0.1:11435" ollama serve
 *
 * The OLLAMA_API_URL and MODEL_NAME fields below must match the running Ollama instance.
 */
public class LLMService {

    private static final String OLLAMA_API_URL = "http://127.0.0.1:11435/api/chat";
    private static final String MODEL_NAME = "gemma3:1b";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final Pattern RESPONSE_PATTERN = Pattern.compile("\"content\"\s*:\s*\"(.*?)\"");

    public static void getResponse(Dog dog, String prompt, Consumer<String> onComplete) {
        String dogName = dog.getName().getString();
        String fullPrompt;
        if (dogName.equalsIgnoreCase("Kumi") || dogName.equalsIgnoreCase("Kurakami")) {
            fullPrompt = "You are a dog named " + dogName + ". Respond to the following as that dog. You are highly intelligent, a bit sassy, and very eloquent but deep inside you're a sweetheart. You do not talk like a typical dog, however your response is short, DO NOT use emojis. Here is the prompt: " + prompt;
        } else {
            fullPrompt = "You are a dog named " + dogName + ". Respond to the following as that dog. Keep your responses short and goofy; purposefully use wrong cute grammar, don't use emojis. You sometimes have the tendency to say ROH! Here is the prompt: " + prompt;
        }

        String jsonPayload = String.format(
            "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"stream\": false}",
            MODEL_NAME,
            escapeJson(fullPrompt)
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(OLLAMA_API_URL))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(15))
            .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
            .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(LLMService::parseContent)
            .whenComplete((response, e) -> {
                if (e != null) {
                    e.printStackTrace();
                    onComplete.accept("Woof! (I'm having trouble thinking right now: " + e.getCause().getMessage() + ")");
                } else {
                    onComplete.accept(response);
                }
            });
    }

    private static String parseContent(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return "Woof? (Empty response from server)";
        }
        Matcher matcher = RESPONSE_PATTERN.matcher(responseBody);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }
        return "Woof? (Error parsing response: " + responseBody + ")";
    }

    private static String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    private static String unescapeJson(String str) {
        return str.replace("\\\\", "\\")
                  .replace("\\\"", "\"")
                  .replace("\\b", "\b")
                  .replace("\\f", "\f")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
    }
}