package com.zlatenov.nospoilerapp.model.form;

import com.zlatenov.nospoilersportsapi.model.dto.RegisterUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterForm {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public static RegisterUserDto buildRegisterDTOFromForm(RegisterForm userForm) {
        return RegisterUserDto.builder()
                .username(userForm.getUsername())
                .password(userForm.getPassword())
                .email(userForm.getEmail())
                .build();
    }
}
