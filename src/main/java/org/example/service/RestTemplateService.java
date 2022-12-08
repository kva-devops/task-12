package org.example.service;

import org.example.model.User;
import org.springframework.http.ResponseEntity;


public interface RestTemplateService {
    ResponseEntity<String> getAllUsers();
    ResponseEntity<String> saveUser(String request, String sessionId);
    ResponseEntity<String> updateUser(String request, String sessionId);
    ResponseEntity<String> deleteUser(Long id, String sessionId);




}
