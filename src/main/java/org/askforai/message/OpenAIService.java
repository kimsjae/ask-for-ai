package org.askforai.message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private static final String API_KEY = System.getenv("GPT_SECRET_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions"; // OpenAI API URL
    
    // 메시지 기록을 저장할 리스트
    private List<Message> messageHistory = new ArrayList<>();

    public String generateResponse(String userMessage) {
        // 사용자의 메시지를 기록에 추가
        messageHistory.add(new Message(Sender.User, userMessage));

        // 메시지 기록을 JSON 배열 형식으로 변환
        StringBuilder messagesBuilder = new StringBuilder();
        for (Message message : messageHistory) {
            messagesBuilder.append(String.format("{\"role\":\"%s\",\"content\":\"%s\"},",
                    message.getSender() == Sender.User ? "user" : "assistant",
                    message.getContent()));
        }

        // 마지막 쉼표 제거
        if (messagesBuilder.length() > 0) {
            messagesBuilder.setLength(messagesBuilder.length() - 1);
        }

        // JSON 요청 바디 설정
        String jsonRequest = String.format(
        	    "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"한국어로 대답해줘: '%s'\"}, %s]}",
        	    userMessage, messagesBuilder.toString()
        	);

        String responseText = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(API_URL);
            post.setHeader("Authorization", "Bearer " + API_KEY);
            post.setHeader("Content-Type", "application/json; charset=UTF-8");
            post.setEntity(new StringEntity(jsonRequest, ContentType.APPLICATION_JSON));

            // API 호출
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                System.out.println("API Response: " + jsonResponse); // 응답 출력
                responseText = parseResponse(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseText;
    }

    private String parseResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            // 응답 형식이 예상과 다른 경우 처리
            if (rootNode.has("choices") && rootNode.path("choices").size() > 0) {
                return rootNode.path("choices").get(0).path("message").path("content").asText();
            } else {
                return "No valid response from AI";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }
}
