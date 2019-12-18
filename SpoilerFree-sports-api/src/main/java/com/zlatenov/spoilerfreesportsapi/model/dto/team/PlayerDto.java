package com.zlatenov.spoilerfreesportsapi.model.dto.team;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDto {

    private String firstName;
    private String lastName;
    private Integer yearsPro;
    private String collegeName;
    private String country;
    private String dateOfBirth;
    private String startNba;
    private Float heightInMeters;
    private Float weightInKilograms;
    private Integer jersey;
    private String position;
}
