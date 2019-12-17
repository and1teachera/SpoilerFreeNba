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
public class StandingsViewModel {

    private TeamViewModel team;
    private Short index;
    private Short conferenceIndex;
    private Short divisionIndex;
    private Short wins;
    private Short losses;
    private Short conferenceWins;
    private Short conferenceLosses;
    private Short divisionWins;
    private Short divisionLosses;
    private Short streak;
    private boolean isWinning;


}
