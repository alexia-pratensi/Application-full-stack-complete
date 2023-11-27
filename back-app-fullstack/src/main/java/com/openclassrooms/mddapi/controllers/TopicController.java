package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.servicesImpl.TopicServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TopicController {

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    /**
     * This method is used to fetch all topics.
     * It sends a GET request and returns a ResponseEntity.
     * If the operation is successful, it returns the list of all topics with an
     * HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the list of all topics or an error message.
     */
    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        try {
            List<TopicDto> topics = topicServiceImpl.getAllTopics();
            return ResponseEntity.ok().body(topics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to fetch a specific topic by its ID.
     * It sends a GET request with a path variable 'id'.
     * If the operation is successful, it returns the topic with the corresponding
     * ID and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id This is the ID of the topic to be fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the topic with the corresponding ID or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findTopicById(@PathVariable Long id) {
        try {
            TopicDto topic = topicServiceImpl.findById(id);
            return ResponseEntity.ok().body(topic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to fetch all posts associated with a specific topic by
     * its ID.
     * It sends a GET request with a path variable 'id'.
     * If the operation is successful, it returns the list of all posts associated
     * with the topic with the corresponding ID and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id This is the ID of the topic whose associated posts are to be
     *           fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the list of all posts associated with the topic with the
     *         corresponding ID or an error message.
     */
    @GetMapping("/{id}/posts")
    public ResponseEntity<?> findPostsByTopicId(@PathVariable Long id) {
        try {
            List<PostDto> posts = topicServiceImpl.findPostsByTopicId(id);
            return ResponseEntity.ok().body(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
