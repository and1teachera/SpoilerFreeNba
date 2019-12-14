package com.zlatenov.nbastandingsservice.model.service;

import com.zlatenov.nbastandingsservice.model.Record;
import com.zlatenov.nbastandingsservice.model.Streak;
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
public class StandingsServiceModel {

    private String teamName;
    private Record teamRecord;
    private Streak streak;
    private String conference;
    private String division;
    private Record conferenceRecord;
    private Record divisionRecord;
}
