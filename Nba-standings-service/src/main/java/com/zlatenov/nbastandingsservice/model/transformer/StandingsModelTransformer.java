package com.zlatenov.nbastandingsservice.model.transformer;

import com.zlatenov.nbastandingsservice.model.entity.Standings;
import com.zlatenov.nbastandingsservice.model.response.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.service.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.service.Team;
import com.zlatenov.spoilerfreesportsapi.model.dto.standings.StandingsDto;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Streak;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class StandingsModelTransformer {

    private final ModelMapper modelMapper;

    public List<StandingsServiceModel> transformResponseToTeamServiceModels(
            List<StandingsResponseModel> standingsResponseModels) {
        return standingsResponseModels.stream()
                .map(this::transformResponseToStandingsServiceModel)
                .collect(Collectors.toList());
    }

    private StandingsServiceModel transformResponseToStandingsServiceModel(
            StandingsResponseModel standingsResponseModel) {
        return StandingsServiceModel.builder()
                .team(Team.builder()
                              .name(standingsResponseModel.getName())
                              .conference(standingsResponseModel.getConference().getName())
                              .division(standingsResponseModel.getDivision().getName())
                              .build())
                .teamRecord(Record.builder()
                                    .win(Short.valueOf(standingsResponseModel.getWin()))
                                    .losses(Short.valueOf(standingsResponseModel.getLoss()))
                                    .build())
                .conferenceRecord(Record.builder()
                                          .win(Short.valueOf(standingsResponseModel.getConference().getWin()))
                                          .losses(Short.valueOf(standingsResponseModel.getConference().getLoss()))
                                          .build())
                .divisionRecord(Record.builder()
                                        .win(Short.valueOf(standingsResponseModel.getDivision().getWin()))
                                        .losses(Short.valueOf(standingsResponseModel.getDivision().getLoss()))
                                        .build())
                .streak(Streak.builder()
                                .gamesBehind(Float.valueOf(standingsResponseModel.getGamesBehind()))
                                .streak(Short.valueOf(standingsResponseModel.getStreak()))
                                .winStreak(Short.valueOf(standingsResponseModel.getWinStreak()))
                                .winPercentage(Float.valueOf(standingsResponseModel.getWinPercentage()))
                                .lossPercentage(Float.valueOf(standingsResponseModel.getLossPercentage()))
                                .tieBreakerPoints(StringUtils.isNotEmpty(standingsResponseModel.getTieBreakerPoints()) ?
                                                          Float.valueOf(standingsResponseModel.getTieBreakerPoints()) :
                                                          null)
                                .lastTenLoss(Short.valueOf(standingsResponseModel.getLastTenLoss()))
                                .lastTenWin(Short.valueOf(standingsResponseModel.getLastTenWin()))
                                .build())
                .build();
    }

    public List<Standings> transformToStandingsEntities(List<StandingsServiceModel> standingsServiceModels) {
        return standingsServiceModels.stream().map(this::transformToStandingsEntity).collect(Collectors.toList());
    }

    public Standings transformToStandingsEntity(StandingsServiceModel standingsServiceModel) {
        return Standings.builder()
                .conference(standingsServiceModel.getTeam().getConference())
                .division(standingsServiceModel.getTeam().getDivision())
                .teamName(standingsServiceModel.getTeam().getName())
                .wins(standingsServiceModel.getTeamRecord().getWin())
                .losses(standingsServiceModel.getTeamRecord().getLosses())
                .conferenceWins(standingsServiceModel.getConferenceRecord().getWin())
                .conferenceLosses(standingsServiceModel.getConferenceRecord().getLosses())
                .divisionLosses(standingsServiceModel.getDivisionRecord().getLosses())
                .divisionWins(standingsServiceModel.getDivisionRecord().getWin())
                .streak(standingsServiceModel.getStreak())
                .date(standingsServiceModel.getDate())
                .build();
    }

    public List<StandingsServiceModel> transformEntitiesToStandingsServiceModels(Iterable<Standings> standingsList) {
        return StreamSupport.stream(standingsList.spliterator(), false)
                .map(this::transformEntityToStandingsServiceModel)
                .collect(Collectors.toList());
    }

    public StandingsServiceModel transformEntityToStandingsServiceModel(Standings standings) {
        return StandingsServiceModel.builder()
                .team(Team.builder()
                              .name(standings.getTeamName())
                              .conference(standings.getConference())
                              .division(standings.getDivision())
                              .build())
                .teamRecord(Record.builder().win(standings.getWins()).losses(standings.getLosses()).build())
                .conferenceRecord(Record.builder()
                                          .win(standings.getConferenceWins())
                                          .losses(standings.getConferenceLosses())
                                          .build())
                .divisionRecord(
                        Record.builder().win(standings.getDivisionWins()).losses(standings.getDivisionLosses()).build())
                .streak(standings.getStreak())
                .date(standings.getDate())
                .build();
    }

    public List<StandingsDto> transformToStandingsDtos(List<StandingsServiceModel> standingsForDate) {
        List<StandingsDto> standingsDtos = new ArrayList<>();
        for (StandingsServiceModel standingsServiceModel : standingsForDate) {
            StandingsDto standingsDto = transformToStandingsDto(standingsServiceModel);
            standingsDto.setPosition(Integer.valueOf(standingsForDate.indexOf(standingsServiceModel) + 1).shortValue());
            standingsDtos.add(standingsDto);
        }
        return standingsDtos;
    }

    private StandingsDto transformToStandingsDto(StandingsServiceModel standingsServiceModel) {
        return StandingsDto.builder()
                .teamName(standingsServiceModel.getTeam().getName())
                .teamRecord(standingsServiceModel.getTeamRecord())
                .conferenceRecord(standingsServiceModel.getConferenceRecord())
                .divisionRecord(standingsServiceModel.getDivisionRecord())
                .streak(standingsServiceModel.getStreak())
                .date(standingsServiceModel.getDate())
                .build();
    }
}
