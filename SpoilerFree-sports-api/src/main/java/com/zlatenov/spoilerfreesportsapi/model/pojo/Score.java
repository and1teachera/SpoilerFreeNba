package com.zlatenov.spoilerfreesportsapi.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Score {

    private Short homeTeamPoints;
    private Short awayTeamPoints;
}
