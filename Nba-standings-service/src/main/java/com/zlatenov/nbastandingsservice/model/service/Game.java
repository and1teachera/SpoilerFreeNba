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

}
