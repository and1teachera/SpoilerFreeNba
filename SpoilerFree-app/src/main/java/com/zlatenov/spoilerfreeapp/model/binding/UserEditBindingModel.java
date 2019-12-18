package com.zlatenov.spoilerfreeapp.model.binding;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEditBindingModel {

    private String password;
    private String newPassword;
    private String confirmPassword;
    private String email;
    private Integer age;
    private String country;
}
