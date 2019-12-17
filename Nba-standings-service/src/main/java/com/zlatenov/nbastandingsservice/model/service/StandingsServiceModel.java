package com.zlatenov.nbastandingsservice.model.service;

import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Streak;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandingsServiceModel {

    private Team team;
    private Record teamRecord;
    private Streak streak;
    private Record conferenceRecord;
    private Record divisionRecord;
    private Date date;

}
