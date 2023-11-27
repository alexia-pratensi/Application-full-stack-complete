package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.ResponseRequest;
import com.openclassrooms.mddapi.dto.SignupRequest;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.dto.UserUpdateRequest;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.repositories.UserIRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.servicesImpl.TopicServiceImpl;
import com.openclassrooms.mddapi.servicesImpl.UserServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserIRepository userRepository;

    @Autowired
    TopicServiceImpl topicServiceImpl;

    /**
     * This method is used to authenticate a user.
     * It sends a POST request with a LoginRequest object in the request body.
     * If the user's email and password match the records, it generates a JWT token
     * and returns it along with the user's details and an HTTP status code of 200
     * (OK).
     * If the user's email and password do not match the records, it throws an
     * exception which is handled by Spring Security's default exception handling.
     *
     * @param loginRequest This is the LoginRequest object that contains the user's
     *                     email and password.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains a
     *         JwtResponse object with the JWT token and the user's details if the
     *         authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        }
        // If the email and password match, create and return a JWT
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName()));
    }

    /**
     * This method is used to update a user's details.
     * It sends a PUT request with a UserUpdateRequest object in the request body
     * and the user's ID as a path variable.
     * If the operation is successful, it updates the user's details in the
     * database, returns a success message "Votre profil a été mis à jour!" and an
     * HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id      This is the ID of the user to be updated.
     * @param request This is the UserUpdateRequest object that contains the new
     *                details of the user.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         a success message or an error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            UserEntityDto user = userService.updateUser(id, request.getName(), request.getEmail(),
                    request.getPassword());
            // Update the user object with the new values
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            // Update the user in the database
            userService.updateUser(id, user.getName(), user.getEmail(), user.getPassword());
            ResponseRequest response = new ResponseRequest("Votre profil a été mis à jour!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to register a new user.
     * It sends a POST request with a SignupRequest object in the request body.
     * If the user's email is already taken, it returns an error message "Error:
     * Email is already taken!" and an HTTP status code of 400 (Bad Request).
     * If the user's email is not taken, it creates a new user's account, saves it
     * in the database, returns a success message "User registered successfully!"
     * and an HTTP status code of 200 (OK).
     *
     * @param signUpRequest This is the SignupRequest object that contains the new
     *                      user's details.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         a success message or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseRequest("Error: Email is already taken!"));
        }
        // Create new user's account
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseRequest("User registered successfully!"));
    }

    /**
     * This method is used to fetch a specific user by their ID.
     * It sends a GET request with a path variable 'id'.
     * If the operation is successful, it returns the user with the corresponding ID
     * and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id This is the ID of the user to be fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the user with the corresponding ID or an error message.
     */
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

    /**
     * This method is used to fetch all topics associated with a specific user by
     * their ID.
     * It sends a GET request with a path variable 'id'.
     * If the operation is successful, it returns the list of all topics associated
     * with the user with the corresponding ID and an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id This is the ID of the user whose associated topics are to be
     *           fetched.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the list of all topics associated with the user with the
     *         corresponding ID or an error message.
     */
    @GetMapping("/{id}/topics")
    public ResponseEntity<?> getTopicsByUserId(@PathVariable Long id) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            UserEntityDto user = userService.findById(id);
            return ResponseEntity.ok().body(user.getTopics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to subscribe a user to a specific topic.
     * It sends a POST request with path variables 'id' and 'topicId'.
     * If the operation is successful, it subscribes the user with the corresponding
     * ID to the topic with the corresponding topicId, fetches the updated list of
     * all topics, and returns it with an HTTP status code of 200 (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id      This is the ID of the user who is subscribing to the topic.
     * @param topicId This is the ID of the topic to which the user is subscribing.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the updated list of all topics or an error message.
     */
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
            List<TopicDto> topicsList = topicServiceImpl.getAllTopics();
            return ResponseEntity.ok(topicsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to unsubscribe a user from a specific topic.
     * It sends a DELETE request with path variables 'id' and 'topicId'.
     * If the operation is successful, it unsubscribes the user with the
     * corresponding ID from the topic with the corresponding topicId, fetches the
     * updated list of all topics, and returns it with an HTTP status code of 200
     * (OK).
     * If an error occurs during the operation, it returns an error message with an
     * HTTP status code of 400 (Bad Request).
     *
     * @param id      This is the ID of the user who is unsubscribing from the
     *                topic.
     * @param topicId This is the ID of the topic from which the user is
     *                unsubscribing.
     * @return ResponseEntity<?> This returns a ResponseEntity that contains either
     *         the updated list of all topics or an error message.
     */
    @DeleteMapping("/{id}/unsubscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, @PathVariable Long topicId) {
        try {
            if (id == null || id == 0) {
                throw new Exception("User id is null or 0");
            }
            if (topicId == null || topicId == 0) {
                throw new Exception("Topic id is null or 0");
            }
            userService.unsubscribe(id, topicId);
            List<TopicDto> topicsList = topicServiceImpl.getAllTopics();
            return ResponseEntity.ok(topicsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
