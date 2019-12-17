package com.zlatenov.spoilerfreeapp.model.binding;

import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RegisterFormBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public static RegisterUserDto buildRegisterDTOFromForm(RegisterFormBindingModel userForm) {
        return RegisterUserDto.builder()
                .username(userForm.getUsername())
                .password(userForm.getPassword())
                .email(userForm.getEmail())
                .build();
    }
}
