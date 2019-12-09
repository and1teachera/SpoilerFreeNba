package com.zlatenov.nospoilerapp.transformer;

import com.zlatenov.nospoilerapp.model.entity.Team;
import com.zlatenov.nospoilersportsapi.model.dto.TeamDto;
import com.zlatenov.nospoilersportsapi.model.dto.TeamsDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */

@Component
public class TeamModelTransformer {
    public List<Team> transformToTeamsList(TeamsDto teamsDto) {
        return teamsDto.getTeamDtos().stream()
                .map(teamDto -> {
                    try {
                        return transformToTeam(teamDto);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return new Team();
                })
                .collect(Collectors.toList());
    }

    private Team transformToTeam(TeamDto teamDto) throws ParseException {
        return Team.builder()
                .fullName(teamDto.getFullName())
                .shortName(teamDto.getShortName())
                .nickName(teamDto.getNickname())
                .city(teamDto.getCity())
                .logo(teamDto.getLogo())
                .confName(teamDto.getConfName())
                .divName(teamDto.getDivName())
                .build();
    }
}
