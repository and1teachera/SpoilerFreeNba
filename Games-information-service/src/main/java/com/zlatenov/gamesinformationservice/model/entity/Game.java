package com.zlatenov.gamesinformationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Game extends BaseEntity {

    private Date startTimeUtc;
    private Date endTimeUtc;
    private String arena;
    private String city;
    private String homeTeam;
    private String awayTeam;
    private Short homeTeamScore;
    private Short awayTeamScore;
}
