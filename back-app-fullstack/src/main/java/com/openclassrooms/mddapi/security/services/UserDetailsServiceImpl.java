package com.openclassrooms.mddapi.security.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.UserIRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserIRepository userRepository;

    /**
     * This method is used to load user details by username, which in this case is
     * the email.
     * It retrieves the user from the repository by email, throws a
     * UsernameNotFoundException if the user is not found,
     * and builds a UserDetailsImpl object from the user details.
     *
     * @param username This is the username (email) of the user.
     * @return UserDetails This returns the details of the user.
     * @throws UsernameNotFoundException This exception is thrown if the user is not
     *                                   found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(username);
        UserEntity user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

}