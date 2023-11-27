package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8)
    private String password;

}
