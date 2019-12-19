package com.zlatenov.spoilerfreeapp.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Player extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
