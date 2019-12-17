package com.zlatenov.spoilerfreeapp.model.binding;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginFormBindingModel {

    private String username;
    private String password;

}
