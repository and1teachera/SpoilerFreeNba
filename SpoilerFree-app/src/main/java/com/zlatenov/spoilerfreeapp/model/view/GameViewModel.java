package com.zlatenov.spoilerfreeapp.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GameViewModel {

    private String homeTeam;
    private String awayTeam;
    private Short homeTeamPoints;
    private Short awayTeamPoints;
    private String date;
}
