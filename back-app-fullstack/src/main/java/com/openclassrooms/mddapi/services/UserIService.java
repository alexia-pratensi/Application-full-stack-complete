package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.UserEntityDto;

public interface UserIService {

    public UserEntityDto createUser(UserEntityDto user);

    public UserEntityDto findUserByEmail(LoginRequest loginRequest);

    public UserEntityDto findById(Long id);

    public UserEntityDto updateUser(Long id, String name, String email, String password);

    public void subscribe(Long id, Long topicDtoId);

    public void unsubscribe(Long id, Long topicDtoId);

    // logout

}
