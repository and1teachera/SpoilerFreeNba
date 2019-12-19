package com.zlatenov.spoilerfreeapp.model.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
public class Standings extends BaseEntity {

    @OneToOne
    private Team team;
    private Short teamPosition;
    private Short conferencePosition;
    private Short divisionPosition;
    private Short wins;
    private Short losses;
    private Short conferenceWins;
    private Short conferenceLosses;
    private Short divisionWins;
    private Short divisionLosses;
    private Short streak;
    private Float winPercentage;
    private Float lossPercentage;
    private Short winStreak;
    private Date date;

}
