package com.zlatenov.spoilerfreesportsapi.model.dto.standings;

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
    private Short loss;

    public void incrementNumberOfWins() {
        win = (short)  (win + 1);
    }

    public void incrementNumberOfLosses() {
        loss = (short)  (loss + 1);
    }

    public static Record createEmptyRecord() {
        return Record.builder().win(ZERO).loss(ZERO).build();
    }

}
