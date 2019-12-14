package com.zlatenov.nbastandingsservice.model.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    private Date date;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;
    private GameInformation gameInformation;

    public boolean isSameConferenceTeamsGame() {
        return homeTeam.getConference().equalsIgnoreCase(awayTeam.getConference());
    }

    public boolean isSameDivisionTeamsGame() {
        return homeTeam.getDivision().equalsIgnoreCase(awayTeam.getDivision());
    }

    public boolean isFinished() {
        return gameInformation.getEndTime() != null;
    }

    public Team getWinner() {
        if (score.getAwayTeamPoints() > score.getHomeTeamPoints()) {
            return awayTeam;
        }
        return homeTeam;
    }

    public Team getLooser() {
        if (score.getAwayTeamPoints() < score.getHomeTeamPoints()) {
            return awayTeam;
        }
        return homeTeam;
    }
}
