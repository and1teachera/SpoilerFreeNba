package com.zlatenov.spoilerfreeapp.model.view;

import lombok.*;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DayStandingsModel {

    private Short index;
    private TeamViewModel team;
    private Short wins;
    private Short losses;
    private Short streak;
    private boolean isWinning;

}
