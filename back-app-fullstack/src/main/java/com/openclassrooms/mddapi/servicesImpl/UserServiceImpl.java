package com.openclassrooms.mddapi.servicesImpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserIRepository;
import com.openclassrooms.mddapi.services.UserIService;
import com.openclassrooms.mddapi.transformers.UserTransformer;

@Service
public class UserServiceImpl implements UserIService {

    @Autowired
    private UserIRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private TopicRepository topicRepository;

    /**
     * This method is used to create a new user.
     * It creates a new user from the provided DTO, saves the user, and returns the
     * DTO of the created user.
     *
     * @param userDto This is the DTO that contains the details of the user.
     * @return UserEntityDto This returns the DTO of the created user.
     */
    @Override
    public UserEntityDto createUser(UserEntityDto userDto) {
        UserEntity user = new UserEntity();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        UserEntity userCreated = userRepository.save(user);
        return userTransformer.entityToDto(userCreated, true);
    }

    /**
     * This method is used to find a user by email.
     * It retrieves the user from the repository using the email provided in the
     * login request.
     * If the user is not found or the password is incorrect, it throws a
     * RuntimeException.
     *
     * @param loginRequest This is the request that contains the email and password
     *                     of the user.
     * @return UserEntityDto This returns the DTO of the retrieved user.
     */
    @Override
    public UserEntityDto findUserByEmail(LoginRequest loginRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        UserEntity user = optionalUser.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return userTransformer.entityToDto(user, true);
    }

    /**
     * This method is used to retrieve a user by its id.
     * It retrieves the user from the repository and transforms it to a DTO.
     *
     * @param id This is the id of the user to be retrieved.
     * @return UserEntityDto This returns the DTO of the retrieved user.
     */
    @Override
    public UserEntityDto findById(Long id) {
        UserEntity user = userRepository.findById(id).get();
        return userTransformer.entityToDto(user, true);
    }

    /**
     * This method is used to update a user.
     * It retrieves the user by its id, updates the user's details, saves the user,
     * and returns the DTO of the updated user.
     *
     * @param id       This is the id of the user to be updated.
     * @param name     This is the new name of the user.
     * @param email    This is the new email of the user.
     * @param password This is the new password of the user.
     * @return UserEntityDto This returns the DTO of the updated user.
     */
    @Override
    public UserEntityDto updateUser(Long id, String name, String email, String password) {
        UserEntity user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserEntity userUpdated = userRepository.save(user);
        return userTransformer.entityToDto(userUpdated, true);
    }

    /**
     * This method is used to subscribe a user to a topic.
     * It retrieves the user and the topic by their ids, adds the topic to the
     * user's topics, and saves the user.
     * If the user is already subscribed to the topic, it throws a RuntimeException.
     *
     * @param id         This is the id of the user.
     * @param topicDtoId This is the id of the topic.
     */
    @Override
    public void subscribe(Long id, Long topicDtoId) {
        UserEntity user = userRepository.findById(id).get();
        Topic topic = topicRepository.findById(topicDtoId).get();

        if (user.getTopics().contains(topic)) {
            throw new RuntimeException("You are already subscribed to this topic");
        }
        user.getTopics().add(topic);
        userRepository.save(user);
    }

    /**
     * This method is used to unsubscribe a user from a topic.
     * It retrieves the user and the topic by their ids, removes the topic from the
     * user's topics, and saves the user.
     * If the user is not subscribed to the topic, it throws a RuntimeException.
     *
     * @param id         This is the id of the user.
     * @param topicDtoId This is the id of the topic.
     */
    @Override
    public void unsubscribe(Long id, Long topicDtoId) {
        UserEntity user = userRepository.findById(id).get();
        Topic topic = topicRepository.findById(topicDtoId).get();

        if (!user.getTopics().contains(topic)) {
            throw new RuntimeException("You are not subscribed to this topic");
        }
        user.getTopics().remove(topic);
        userRepository.save(user);
    }

}
