package com.zlatenov.spoilerfreeapp.model.entity;


import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Standings extends BaseEntity {

    @OneToOne
    private Team team;
    private Short index;
    private Short conferenceIndex;
    private Short divisionIndex;
    private Record record;
    private Record conferenceRecord;
    private Record divisionRecord;
    private Short streak;
    private Float winPercentage;
    private Float lossPercentage;
    private Short winStreak;
    private Date date;

}
