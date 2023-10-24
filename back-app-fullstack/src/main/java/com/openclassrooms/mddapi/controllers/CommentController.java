package com.openclassrooms.mddapi.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.servicesImpl.CommentServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/comments")
    public ResponseEntity<?> createCommentDto(
            @RequestParam("date") Date date,
            @RequestParam("content") String content,
            @RequestParam("user") UserEntity user) {

        try {
            if (date == null) {
                throw new Exception("Comment date is null");
            }
            if (content == null || content.isEmpty()) {
                throw new Exception("Comment content is null or empty");
            }
            if (user == null) {
                throw new Exception("Comment user is null");
            }

            commentService.createCommentDto(date, content, user);

            return ResponseEntity.ok("Comment created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
