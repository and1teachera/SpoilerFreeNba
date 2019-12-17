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
public class UserRoleBindingModel {

    private String username;
    private String role;

}
