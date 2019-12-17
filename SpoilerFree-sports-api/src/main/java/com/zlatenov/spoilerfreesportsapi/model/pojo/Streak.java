package com.zlatenov.spoilerfreesportsapi.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.zlatenov.spoilerfreesportsapi.util.Constants.ZERO;
import static com.zlatenov.spoilerfreesportsapi.util.Constants.ZERO_FLOAT;

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

    public static Streak createEmptyStreak() {
        return Streak.builder()
                .gamesBehind(ZERO_FLOAT)
                .lastTenWin(ZERO)
                .lastTenLoss(ZERO)
                .streak(ZERO)
                .winPercentage(ZERO_FLOAT)
                .lossPercentage(ZERO_FLOAT)
                .winStreak(ZERO)
                .tieBreakerPoints(ZERO_FLOAT)
                .build();
    }

    public void clearWinningStreak() {
        winStreak = (short)  0;
        streak = (short) 0;
    }

    public void incrementWinningStreak() {
        winStreak = (short)  1;
        streak = (short)  (streak + 1);
    }

    public void recalculatePct(Record teamRecord) {
        winPercentage = teamRecord.getWin().floatValue() / (teamRecord.getWin() + teamRecord.getLoss());
        lossPercentage = teamRecord.getLoss().floatValue() / (teamRecord.getWin() + teamRecord.getLoss());
    }
}
