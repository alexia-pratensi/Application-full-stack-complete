package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.TopicDto;
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

    @Override
    public UserEntityDto createUser(UserEntityDto userDto) {
        UserEntity user = new UserEntity();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        UserEntity userCreated = userRepository.save(user);
        return userTransformer.entityToDto(userCreated, true);
    }

    @Override
    public UserEntityDto findUserByEmail(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return userTransformer.entityToDto(user, true);
    }

    @Override
    public UserEntityDto findById(Long id) {
        UserEntity user = userRepository.findById(id).get();
        return userTransformer.entityToDto(user, true);
    }

    @Override
    public UserEntityDto updateUser(Long id, String name, String email, String password) {
        UserEntity user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserEntity userUpdated = userRepository.save(user);
        return userTransformer.entityToDto(userUpdated, true);
    }

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
