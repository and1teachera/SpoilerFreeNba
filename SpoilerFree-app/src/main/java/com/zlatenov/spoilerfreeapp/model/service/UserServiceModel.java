package com.zlatenov.spoilerfreeapp.model.service;

import com.zlatenov.spoilerfreeapp.model.enums.Role;
import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserServiceModel {

    private String name;
    private String email;
    private Role role;


}
