package com.zlatenov.nbastandingsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Streak {

    private Float gamesBehind;
    private Short lastTenWin;
    private Short lastTenLoss;
    private Short streak;
    private Float winPercentage;
    private Float lossPercentage;
    private Short winStreak;
    private Float tieBreakerPoints;

}
