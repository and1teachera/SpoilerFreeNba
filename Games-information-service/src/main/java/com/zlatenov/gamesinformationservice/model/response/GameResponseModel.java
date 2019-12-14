package com.zlatenov.gamesinformationservice.model.response;

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
public class GameResponseModel {

    private String startTimeUTC;
    private String endTimeUTC;
    private String arena;
    private String city;
    private TeamResponseModel hTeam;
    private TeamResponseModel vTeam;
}
