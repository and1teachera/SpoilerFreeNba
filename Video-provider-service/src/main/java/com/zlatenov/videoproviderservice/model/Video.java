package com.zlatenov.videoproviderservice.model;

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
public class Video {

    private String id;
    private String duration;
    private String name;
    private String channel;
    private String homeTeamName;
    private String awayTeamName;
    private String date;
}
