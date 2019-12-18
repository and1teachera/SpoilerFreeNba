package com.zlatenov.teamsinformationservice.model.service;

import lombok.*;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerServiceModel {

    private String firstName;
    private String lastName;
    private String teamId;
    private TeamServiceModel teamServiceModel;
    private Integer yearsPro;
    private String collegeName;
    private String country;
    private Date dateOfBirth;
    private String startNba;
    private Float heightInMeters;
    private Float weightInKilograms;
    private Integer jersey;
    private String position;
    private boolean isActive;
}
