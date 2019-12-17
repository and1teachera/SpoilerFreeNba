package com.zlatenov.videoproviderservice.model;

import com.zlatenov.spoilerfreesportsapi.model.pojo.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {

    private Date date;
    private String homeTeamName;
    private String awayTeamName;
    private Score score;
}
