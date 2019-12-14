package com.zlatenov.nbastandingsservice.model.response;

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
public class StandingsResponseModel {

    private String teamId;
    private String name;
    private String league;
    private String win;
    private String loss;
    private String gamesBehind;
    private String lastTenWin;
    private String lastTenLoss;
    private String streak;
    private String seasonYear;
    private String winPercentage;
    private String lossPercentage;
    private String winStreak;
    private String tieBreakerPoints;
    private ConferenceResponseModel conference;
    private DivisionResponseModel division;
    private RecordResponseModel home;
    private RecordResponseModel away;


}
