package com.zlatenov.spoilerfreesportsapi.model.dto.video;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideoDto {

    private String id;
    private String duration;
    private String name;
    private String homeTeamName;
    private String awayTeamName;
    private String date;

}
