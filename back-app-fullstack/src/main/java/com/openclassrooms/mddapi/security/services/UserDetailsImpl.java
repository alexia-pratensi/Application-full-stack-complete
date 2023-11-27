package com.openclassrooms.mddapi.security.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * This class implements the UserDetails interface and provides the details of a
 * user.
 * It includes the user's id, name, username, and password, along with methods
 * to get the authorities of the user,
 * check if the account is not expired, not locked, credentials are not expired,
 * and if the account is enabled.
 * It also includes a method to check if two UserDetailsImpl objects are equal
 * based on their id.
 */
@Builder
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
