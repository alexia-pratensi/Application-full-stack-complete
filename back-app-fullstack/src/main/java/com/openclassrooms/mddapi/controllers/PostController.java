package com.openclassrooms.mddapi.controllers;

import java.util.Date;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.ResponseRequest;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.servicesImpl.PostServiceImpl;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    // @Autowired
    // private PostMapper postMapper;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.ok().body(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        log.info(postDto);

        // Post post = this.postService.createPost();

        // log.info(post);
        return ResponseEntity.ok().body(postDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            if (id == null || id == 0) {
                throw new Exception("Post id is null or 0");
            }
            Post post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

// @PostMapping("/posts")
// public ResponseEntity<?> createPostDto(
// @Valid @RequestParam("title") String title,
// @Valid @RequestParam("user") UserEntity user,
// @Valid @RequestParam("date") Date date,
// @Valid @RequestParam("content") String content,
// @Valid @RequestParam("topic") Topic topic) {

// try {
// if (title == null || title.isEmpty()) {
// throw new Exception("Post title is null or empty");
// }
// if (user == null) {
// throw new Exception("Post user is null");
// }
// if (date == null) {
// throw new Exception("Post date is null");
// }
// if (content == null || content.isEmpty()) {
// throw new Exception("Post content is null or empty");
// }
// if (topic == null) {
// throw new Exception("Post topic is null");
// }

// postService.createPostDto(title, user, date, content, topic);

// ResponseRequest response = new ResponseRequest("Rental created!");
// return ResponseEntity.status(HttpStatus.CREATED).body(response);
// } catch (Exception e) {
// return ResponseEntity.badRequest().body("Error: " + e.getMessage());
// }
// }

// @PostMapping("/posts")
// public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
// try {
// postService.createPostDto(postDto);

// ResponseRequest response = new ResponseRequest("Rental created!");
// return ResponseEntity.status(HttpStatus.CREATED).body(response);
// } catch (Exception e) {
// return ResponseEntity.badRequest().body("Error: " + e.getMessage());
// }
// }

// @GetMapping("/posts")
// public ResponseEntity<?> getAllPosts() {
// try {
// List<PostDto> posts = postService.getAllPosts();
// return ResponseEntity.ok(posts);
// } catch (Exception e) {
// return ResponseEntity.badRequest().body("Error: " + e.getMessage());
// }
// }