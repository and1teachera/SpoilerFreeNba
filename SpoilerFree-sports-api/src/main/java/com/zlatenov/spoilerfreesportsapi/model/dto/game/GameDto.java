package com.zlatenov.spoilerfreesportsapi.model.dto.game;

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
public class GameDto {

    private String date;
    private String homeTeamName;
    private String awayTeamName;
    private GameInformationDto gameInformationDto;

}
