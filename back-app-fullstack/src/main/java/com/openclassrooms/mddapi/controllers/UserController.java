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
import com.openclassrooms.mddapi.dto.UserUpdateRequest;
import com.openclassrooms.mddapi.models.UserEntity;
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

            UserEntity user = userService.findUserByEmail(loginRequest);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {
        try {
            if (user == null) {
                throw new Exception("User is null");
            }
            userService.createUser(user);
            return ResponseEntity.ok(user);
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

            UserEntity user = userService.updateUser(id, request.getName(), request.getEmail(), request.getPassword());

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

            UserEntity user = userService.findById(id);

            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}

// @PostMapping("/subscribe")
// public ResponseEntity<?> subscribe(UserEntity user, Topic topic) {
// try {
// if (user == null) {
// throw new Exception("User is null");
// }
// if (topic == null) {
// throw new Exception("Topic is null");
// }

// userService.subscribe(user, topic);

// return ResponseEntity.ok("User subscribed to topic");
// } catch (Exception e) {
// return ResponseEntity.badRequest().body("Error: " + e.getMessage());
// }
// }

// @PostMapping("/unsubscribe")
// public ResponseEntity<?> unsubscribe(UserEntity user, Topic topic) {
// try {
// if (user == null) {
// throw new Exception("User is null");
// }
// if (topic == null) {
// throw new Exception("Topic is null");
// }

// userService.unsubscribe(user, topic);

// return ResponseEntity.ok("User unsubscribed to topic");
// } catch (Exception e) {
// return ResponseEntity.badRequest().body("Error: " + e.getMessage());
// }
// }