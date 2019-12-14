package com.zlatenov.nbastandingsservice.model.service;

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
public class GameInformation {

    private String startTime;
    private String endTime;
    private String arena;
    private String city;
}
