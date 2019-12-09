package com.zlatenov.nospoilersportsapi.model.dto;

import com.zlatenov.nospoilersportsapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterUserDto {

    private String username;
    private String email;
    private String password;
    private Role role;

}
