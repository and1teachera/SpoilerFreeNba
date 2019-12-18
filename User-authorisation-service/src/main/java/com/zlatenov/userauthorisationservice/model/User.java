package com.zlatenov.userauthorisationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Angel Zlatenov
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "USER")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private Integer age;

    @Column(name = "country", length = 50)
    private String country;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

}
