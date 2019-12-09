package com.zlatenov.nospoilersportsapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameInformationDto {

    private String startTime;
    private String endTime;
    private String arena;
//    private String city;
//    private String gameDuration;
    private TeamScoreDto homeTeam;
    private TeamScoreDto awayTeam;
}
