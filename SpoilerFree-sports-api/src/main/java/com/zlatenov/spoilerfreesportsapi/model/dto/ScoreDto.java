package com.zlatenov.spoilerfreesportsapi.model.dto;

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
public class ScoreDto {

    private TeamScoreDto homeTeam;
    private TeamScoreDto awayTeam;
}
