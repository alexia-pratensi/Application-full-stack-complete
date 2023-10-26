package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.UserIRepository;
import com.openclassrooms.mddapi.services.UserIService;

@Service
public class UserServiceImpl implements UserIService {

    @Autowired
    private UserIRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserEntity findUserByEmail(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return user;
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntity updateUser(Long id, String name, String email, String password) {
        UserEntity user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserEntity userUpdated = userRepository.save(user);
        // UserEntityDto userDto = modelMapper.map(userUpdated, UserEntityDto.class);
        return userUpdated;
    }

    // @Override
    // public void subscribe(UserEntity user, Topic topic) {
    // UserEntity currentUser = findById(user.getId());

    // boolean alreadyParticipate = currentUser.getTopics().stream().anyMatch(t ->
    // t.getId().equals(currentUser));
    // if (alreadyParticipate) {
    // throw new RuntimeException("You are already subscribed to this topic");
    // }

    // currentUser.getTopics().add(topic);
    // userRepository.save(currentUser);
    // }

    // @Override
    // public void unsubscribe(UserEntity user, Topic topic) {
    // UserEntity currentUser = findById(user.getId());

    // boolean alreadyParticipate = currentUser.getTopics().stream().anyMatch(t ->
    // t.getId().equals(currentUser));
    // if (!alreadyParticipate) {
    // throw new RuntimeException("You are not subscribed to this topic");
    // }

    // currentUser.getTopics().remove(topic);
    // userRepository.save(currentUser);
    // }

}
