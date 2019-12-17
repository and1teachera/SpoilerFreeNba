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
@Builder
@Getter
@Setter
public class GameViewModel {

    private TeamViewModel homeTeam;
    private TeamViewModel awayTeam;
    private Short homeTeamPoints;
    private Short awayTeamPoints;
    private String date;

}
