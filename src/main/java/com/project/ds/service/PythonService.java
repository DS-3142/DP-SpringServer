package com.project.ds.service;

import com.project.ds.dto.response.PythonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PythonService {
    private final RestTemplate restTemplate;

    public PythonResponse requestSummary(Long meetingId, String content) {
        String url = "http://localhost:5001/api/meeting";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = Map.of("meetingId", meetingId,"content", content);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<PythonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                PythonResponse.class
        );

        return response.getBody();
    }
}