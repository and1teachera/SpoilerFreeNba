package com.zlatenov.nbastandingsservice.model.transformer;

import com.zlatenov.nbastandingsservice.model.service.Team;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class TeamsTransformer {

    private final ModelMapper modelMapper;

    public List<Team> transformDtoListToTeams(List<TeamDto> teamDtos) {
        return teamDtos.stream().map(this::transformDtoToTeam).collect(Collectors.toList());
    }

    private Team transformDtoToTeam(TeamDto teamDto) {
        return Team.builder()
                .name(teamDto.getFullName())
                .conference(teamDto.getConfName())
                .division(teamDto.getDivName())
                .build();
    }
}
