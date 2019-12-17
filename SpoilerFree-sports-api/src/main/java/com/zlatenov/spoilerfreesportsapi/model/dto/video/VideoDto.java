package com.zlatenov.spoilerfreesportsapi.model.dto.video;

import lombok.*;

import java.util.List;

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
    private List<String> teamNames;
    private String date;

}
