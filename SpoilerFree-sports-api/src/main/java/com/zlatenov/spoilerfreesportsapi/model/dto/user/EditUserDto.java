package com.zlatenov.spoilerfreesportsapi.model.dto.user;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditUserDto {

    private String username;
    private String email;
    private Integer age;
    private String country;
    private String password;
}
