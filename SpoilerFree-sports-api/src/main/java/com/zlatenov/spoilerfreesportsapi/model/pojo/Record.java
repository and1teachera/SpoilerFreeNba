package com.zlatenov.spoilerfreesportsapi.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.zlatenov.spoilerfreesportsapi.util.Constants.ZERO;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Record {

    private Short win;
    private Short losses;

    public void incrementNumberOfWins() {
        win = (short)  (win + 1);
    }

    public void incrementNumberOfLosses() {
        losses = (short)  (losses + 1);
    }

    public static Record createEmptyRecord() {
        return Record.builder().win(ZERO).losses(ZERO).build();
    }

}
