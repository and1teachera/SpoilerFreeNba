package com.zlatenov.teamsinformationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponseModel {

    private String firstName;
    private String lastName;
    private String teamId;
    private Integer yearsPro;
    private String collegeName;
    private String country;
    private String dateOfBirth;
    private String startNba;
    private String heightInMeters;
    private String weightInKilograms;
    private PlayerInformation leagues;

}
