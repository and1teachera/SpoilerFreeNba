package com.zlatenov.spoilerfreesportsapi.model.dto.standings;

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
public class StandingsDto {

    private String teamName;
    private Short position;
    private Record teamRecord;
    private Record conferenceRecord;
    private Record divisionRecord;
    private Streak streak;
    private Date date;
}
