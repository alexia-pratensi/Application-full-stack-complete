package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.servicesImpl.CommentServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        try {

            this.commentService.createComment(commentDto, postId);
            return ResponseEntity.ok("Comment created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        try {
            return ResponseEntity.ok().body(commentService.getAllComments());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
