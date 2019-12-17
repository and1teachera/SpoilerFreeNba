package com.zlatenov.spoilerfreeapp.model.service;

import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Streak;
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
public class StandingsServiceModel {

    private TeamServiceModel team;
    private Short index;
    private Short conferenceIndex;
    private Short divisionIndex;
    private Record record;
    private Record conferenceRecord;
    private Record divisionRecord;
    private Streak streak;
    private Date date;


}
