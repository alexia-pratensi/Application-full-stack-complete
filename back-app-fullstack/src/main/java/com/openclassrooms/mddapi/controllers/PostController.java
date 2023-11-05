package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.ResponseRequest;
import com.openclassrooms.mddapi.servicesImpl.CommentServiceImpl;
import com.openclassrooms.mddapi.servicesImpl.PostServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDto> posts = postService.getAllPosts();
            return ResponseEntity.ok().body(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto post) {
        try {
            this.postService.createPost(post);
            ResponseRequest response = new ResponseRequest("Post created!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            if (id == null || id == 0) {
                throw new Exception("Post id is null or 0");
            }
            PostDto post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        try {

            this.commentService.createComment(commentDto, postId);
            ResponseRequest response = new ResponseRequest("Comment created!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok().body(commentService.getAllComments(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
