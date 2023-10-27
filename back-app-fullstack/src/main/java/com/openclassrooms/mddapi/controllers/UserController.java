package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.dto.UserUpdateRequest;
import com.openclassrooms.mddapi.servicesImpl.UserServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {

            UserEntityDto user = userService.findUserByEmail(loginRequest);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntityDto userDto) {
        try {
            if (userDto == null) {
                throw new Exception("User is null");
            }
            userService.createUser(userDto);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }

            UserEntityDto user = userService.updateUser(id, request.getName(), request.getEmail(),
                    request.getPassword());

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }

            UserEntityDto user = userService.findById(id);

            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @PathVariable Long topicId) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            if (topicId == null || topicId == 0) {
                throw new Exception("Topic id is null or 0");
            }

            userService.subscribe(id, topicId);

            return ResponseEntity.ok("User subscribed to topic");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/unsubscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, @PathVariable Long topicId) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            if (topicId == null || topicId == 0) {
                throw new Exception("Topic id is null or 0");
            }

            userService.unsubscribe(id, topicId);

            return ResponseEntity.ok("User unsubscribed to topic");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
