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

    /**
     * This method is used to fetch all posts.
     * It sends a GET request and returns a ResponseEntity.
     * If the operation is successful, it returns the list of all posts with an HTTP
     * status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the list of all posts or an error message.
     */
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDto> posts = postService.getAllPosts();
            return ResponseEntity.ok().body(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to create a new post.
     * It sends a POST request with a PostDto object in the request body.
     * If the operation is successful, it returns a response with a message "Post
     * created!" and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param post This is the PostDto object that contains the data for the new
     *             post.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         a success message or an error message.
     */
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

    /**
     * This method is used to fetch a specific post by its ID.
     * It sends a GET request with a path variable 'id'.
     * If the operation is successful, it returns the post with the corresponding ID
     * and an HTTP status code of 200 (OK).
     * If the ID is null or 0, or if any other error occurs during the operation, it
     * returns an error message with an HTTP status code of 400 (Bad Request).
     *
     * @param id This is the ID of the post to be fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the post with the corresponding ID or an error message.
     */
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

    /**
     * This method is used to create a new comment on a specific post.
     * It sends a POST request with a CommentDto object in the request body and the
     * post's ID as a path variable.
     * If the operation is successful, it returns a response with a message "Le
     * commentaire a bien été crée!" and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param commentDto This is the CommentDto object that contains the data for
     *                   the new comment.
     * @param postId     This is the ID of the post on which the comment is to be
     *                   created.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         a success message or an error message.
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        try {
            this.commentService.createComment(commentDto, postId);
            ResponseRequest response = new ResponseRequest("Le commentaire a bien été crée!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to fetch all comments of a specific post by its ID.
     * It sends a GET request with a path variable 'postId'.
     * If the operation is successful, it returns the list of all comments of the
     * post with the corresponding ID and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param postId This is the ID of the post whose comments are to be fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the list of all comments of the post with the corresponding ID or an
     *         error message.
     */
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok().body(commentService.getAllComments(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
