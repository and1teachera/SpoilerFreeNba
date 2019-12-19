package com.zlatenov.spoilerfreeapp.model.service;

import lombok.*;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlayerServiceModel {

    private String firstName;
    private String lastName;
    private Integer yearsPro;
    private String collegeName;
    private String country;
    private Date dateOfBirth;
    private String startNba;
    private Float heightInMeters;
    private Float weightInKilograms;
    private Integer jersey;
    private String position;

    private TeamServiceModel team;
}
