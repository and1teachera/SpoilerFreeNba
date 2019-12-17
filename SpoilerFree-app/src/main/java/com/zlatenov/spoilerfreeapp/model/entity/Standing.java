package com.zlatenov.spoilerfreeapp.model.entity;

import lombok.*;

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
@Builder
public class Standing extends BaseEntity {

    private Team team;
    private Short index;
    private Short conferenceIndex;
    private Short divisionIndex;
    private Short wins;
    private Short losses;
    private Short conferenceWins;
    private Short conferenceLosses;
    private Short divisionWins;
    private Short divisionLosses;
    private Short streak;
    private boolean isWinning;
    private Date date;

}
