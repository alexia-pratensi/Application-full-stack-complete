package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.servicesImpl.UserServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    // @Autowired
    // private UserMapper userMapper;
    //
    // @GetMapping("/users/{id}")
    // public ResponseEntity<?> getCurrentUser(Long id) {
    // try {
    //// UserEntity user = userService.getCurrentUser(id);
    //// return ResponseEntity.ok();
    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    // }
    // }

    @PostMapping("/register")
    public ResponseEntity<?> register(String name, String email, String password, List<Topic> topics) {
        try {
            if (name == null || name.isEmpty()) {
                throw new Exception("User name is null or empty");
            }
            if (email == null || email.isEmpty()) {
                throw new Exception("User email is null or empty");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("User password is null or empty");
            }
            if (topics == null) {
                throw new Exception("User topics is null");
            }

            UserEntityDto user = userService.createUser(name, email, password, topics);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String email, String password) {
        try {
            if (email == null || email.isEmpty()) {
                throw new Exception("User email is null or empty");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("User password is null or empty");
            }

            UserEntityDto user = userService.login(email, password);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(Long id, String name, String email, String password, List<Topic> topics) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            if (name == null || name.isEmpty()) {
                throw new Exception("User name is null or empty");
            }
            if (email == null || email.isEmpty()) {
                throw new Exception("User email is null or empty");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("User password is null or empty");
            }
            if (topics == null) {
                throw new Exception("User topics is null");
            }

            UserEntityDto user = userService.updateUser(id, name, email, password, topics);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
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

    @GetMapping("/users/{id}")
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
