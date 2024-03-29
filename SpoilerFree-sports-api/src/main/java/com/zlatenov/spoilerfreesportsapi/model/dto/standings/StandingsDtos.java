package com.zlatenov.spoilerfreesportsapi.model.dto.standings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StandingsDtos {

    List<StandingsDto> standingsDtos;

}
