package com.zlatenov.spoilerfreesportsapi.model.dto.user;

import com.zlatenov.spoilerfreesportsapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;
    private Role role;
    private Date lastLoginDate;
}
