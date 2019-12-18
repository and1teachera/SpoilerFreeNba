package com.zlatenov.teamsinformationservice.model.response;

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
public class RapidApiPlayersResponseModel {

    private Integer status;
    private String message;
    private Integer results;
    private List<String> filters;
    private List<PlayerResponseModel> players;


}
