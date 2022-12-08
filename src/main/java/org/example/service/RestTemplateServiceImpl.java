package org.example.service;

import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import org.apache.http.client.HttpClient;

public class RestTemplateServiceImpl implements RestTemplateService {

    private final String API_URL = "http://94.198.50.185:7081/api/users";

    private final RestTemplate restTemplate = getTemplate();

    @Override
    public ResponseEntity<String> getAllUsers() {
        return restTemplate.getForEntity(API_URL, String.class);
    }

    @Override
    public ResponseEntity<String> saveUser(String userJson, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Set-Cookie", sessionId);
        HttpEntity<String> request = new HttpEntity<>(userJson, headers);
        return restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

    }

    @Override
    public ResponseEntity<String> updateUser(String updatedUserJson, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Set-Cookie", sessionId);
        HttpEntity<String> request = new HttpEntity<>(updatedUserJson, headers);
        return restTemplate.exchange(API_URL, HttpMethod.PUT, request, String.class);
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Set-Cookie", sessionId);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = API_URL + "/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
    }

    private RestTemplate getTemplate() {
        HttpClient client = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
        return restTemplate;
    }
}
