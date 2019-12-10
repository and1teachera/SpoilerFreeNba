package com.zlatenov.nbastandingsservice.model.entity;

import com.zlatenov.nbastandingsservice.model.Streak;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Angel Zlatenov
 */
@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Standings {

    @Id
    private ObjectId id;
    private String teamName;
    private Short wins;
    private Short losses;
    private Streak streak;
    private String conference;
    private String division;
    private Short conferenceWins;
    private Short conferenceLosses;
    private Short divisionWins;
    private Short divisionLosses;
    private Date date;
}
