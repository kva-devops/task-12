package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.example.service.RestTemplateService;
import org.example.service.RestTemplateServiceImpl;
import org.springframework.http.ResponseEntity;

public class App 
{
    public static StringBuffer code = new StringBuffer();

    public static void main( String[] args ) {
//        Create user model and service
        RestTemplateService restTemplateService = new RestTemplateServiceImpl();
        User user = new User(3L, "James", "Brown", (byte) 35);
//        Update information
        Long id = 3L;
        String nameForUpdate = "Thomas";
        String lastNameForUpdate = "Shelby";

//      1.  Getting all users
        ResponseEntity<String> allUsersResponse = restTemplateService.getAllUsers();

//      2.  Extracting and save 'session ID'
        String sessionId = String.valueOf(allUsersResponse.getHeaders().get("Set-Cookie"));

//      3. Save user and first part of code
        ResponseEntity<String> saveUserResponse = restTemplateService.saveUser(mapper(user), sessionId);
        code.append(saveUserResponse.getBody());

//      4. Update user and second part of code
        user.setName(nameForUpdate);
        user.setLastName(lastNameForUpdate);
        ResponseEntity<String> updateUserResponse = restTemplateService.updateUser(mapper(user), sessionId);
        code.append(updateUserResponse.getBody());

//      5. Delete user and third part of code
        ResponseEntity<String> deleteUserResponse = restTemplateService.deleteUser(id, sessionId);
        code.append(deleteUserResponse.getBody());

//      Show result
        if (code.length() == 18) {
            System.out.println("Success");
            System.out.println("Code: " + code.toString());
        } else {
            System.out.println("Error");
        }
    }

    private static String mapper(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
