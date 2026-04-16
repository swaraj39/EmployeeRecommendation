package com.example.demo.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class EmailController {

    @GetMapping("/email/api")
    public ResponseEntity<?> getEmailApi() {
        String webhookUrl = "https://webhooks.workato.com/webhooks/rest/6ae3b894-92be-458a-85e3-6fd8e0c20ed6/email-service";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("to", "test@example.com");
        body.put("subject", "Hello from Spring Boot");
        body.put("message", "This email was triggered via Workato!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(webhookUrl, request, String.class);
        return ResponseEntity.ok().build();
    }
}