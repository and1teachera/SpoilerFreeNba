package com.zlatenov.nbastandingsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RapidApiStandingsResponse {

    private Integer status;
    private String message;
    private Integer results;
    private List<StandingsResponseModel> standings;
}
