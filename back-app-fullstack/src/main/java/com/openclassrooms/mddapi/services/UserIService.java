package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.dto.LoginRequest;

public interface UserIService {

    public UserEntity createUser(UserEntity user);

    public UserEntity findUserByEmail(LoginRequest loginRequest);

    public UserEntity findById(Long id);

    public UserEntity updateUser(Long id, String name, String email, String password);

    // public void subscribe(UserEntity user, Topic topic);

    // public void unsubscribe(UserEntity user, Topic topic);

}
