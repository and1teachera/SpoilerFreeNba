package com.zlatenov.gamesinformationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
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
public class Game extends BaseEntity {

    private Date startTimeUtc;
    private Date endTimeUtc;
    private String arena;
    private String city;
    private String gameDuration;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "homeTeam_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "awayTeam_id", nullable = false)
    private Team awayTeam;

    private Short homeTeamScore;
    private Short awayTeamScore;
}
