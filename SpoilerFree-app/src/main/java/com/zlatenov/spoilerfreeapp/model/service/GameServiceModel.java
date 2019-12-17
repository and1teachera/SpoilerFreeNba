package com.zlatenov.spoilerfreeapp.model.service;

import com.zlatenov.spoilerfreesportsapi.model.pojo.Score;
import lombok.*;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GameServiceModel {

    private TeamServiceModel homeTeam;
    private TeamServiceModel awayTeam;
    private Score score;
    private Date date;

}
