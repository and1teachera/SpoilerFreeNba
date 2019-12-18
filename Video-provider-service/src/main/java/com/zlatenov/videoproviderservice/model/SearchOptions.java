package com.zlatenov.videoproviderservice.model;

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
public class SearchOptions {

    private String videoName;
    private String homeTeamName;
    private String awayTeamName;
    private String channel;
    private String duration;
    private Date date;
}
