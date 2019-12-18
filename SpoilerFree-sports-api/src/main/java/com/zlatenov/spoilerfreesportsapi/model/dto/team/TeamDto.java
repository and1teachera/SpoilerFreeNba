package com.zlatenov.spoilerfreesportsapi.model.dto.team;

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
public class TeamDto {

    private String city;
    private String fullName;
    private String nickname;
    private String logo;
    private String shortName;
    private String confName;
    private String divName;
    private List<PlayerDto> playerDtos;
}
