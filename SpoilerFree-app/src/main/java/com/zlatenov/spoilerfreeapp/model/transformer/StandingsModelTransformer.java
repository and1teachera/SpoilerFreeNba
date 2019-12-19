package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Standings;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.DayStandingsModel;
import com.zlatenov.spoilerfreeapp.model.view.StandingsViewModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.standings.StandingsDto;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Streak;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class StandingsModelTransformer {

    private final TeamModelTransformer teamModelTransformer;

    public List<StandingsViewModel> transformServiceModelsToViews(List<StandingsServiceModel> standings) {
        return standings.stream().map(this::transformServiceModelToView).collect(Collectors.toList());
    }

    public List<DayStandingsModel> transformServiceModelsToDayViews(List<StandingsServiceModel> standings) {
        return standings.stream().map(this::transformStandingsToDayView).collect(Collectors.toList());
    }

    public DayStandingsModel transformStandingsToDayView(StandingsServiceModel standingsServiceModel) {
        return DayStandingsModel.builder()
                .index(standingsServiceModel.getIndex())
                .wins(standingsServiceModel.getRecord().getWin())
                .losses(standingsServiceModel.getRecord().getLosses())
                .team(teamModelTransformer.transformToTeamViewModel(standingsServiceModel.getTeam()))
                .isWinning(standingsServiceModel.getStreak().getWinStreak() == 1)
                .streak(standingsServiceModel.getStreak().getStreak())
                .build();
    }

    public StandingsViewModel transformServiceModelToView(StandingsServiceModel standingsForTeam) {
        return StandingsViewModel.builder()
                .team(teamModelTransformer.transformToTeamViewModel(standingsForTeam.getTeam()))
                .index(standingsForTeam.getIndex())
                .conferenceIndex(standingsForTeam.getIndex())
                .divisionIndex(standingsForTeam.getIndex())
                .wins(standingsForTeam.getRecord().getWin())
                .losses(standingsForTeam.getRecord().getLosses())
                .wins(standingsForTeam.getRecord().getWin())
                .losses(standingsForTeam.getRecord().getLosses())
                .wins(standingsForTeam.getRecord().getWin())
                .losses(standingsForTeam.getRecord().getLosses())
                .streak(standingsForTeam.getStreak().getStreak())
                .isWinning(standingsForTeam.getStreak().getWinStreak() == 1)
                .build();
    }

    public List<StandingsServiceModel> transformToServiceModels(List<Standings> standings) {
        return standings.stream().map(this::transformToServiceModel).collect(Collectors.toList());
    }

    public StandingsServiceModel transformToServiceModel(Standings standings) {
        return StandingsServiceModel.builder()
                .team(teamModelTransformer.transformToTeamServiceModel(standings.getTeam()))
                .index(standings.getTeamPosition())
                .conferenceIndex(standings.getConferencePosition())
                .divisionIndex(standings.getTeamPosition())
                .record(Record.builder()
                        .win(standings.getWins())
                        .losses(standings.getLosses())
                        .build())
                .conferenceRecord(Record.builder()
                        .win(standings.getConferenceWins()).losses(standings.getConferenceLosses()).build())
                .divisionRecord(Record.builder()
                        .win(standings.getDivisionWins()).losses(standings.getDivisionLosses()).build())
                .winPercentage(standings.getWinPercentage())
                .lossPercentage(standings.getLossPercentage())
                .streak(Streak.builder().streak(standings.getStreak()).winStreak(standings.getWinStreak()).build())
                .date(standings.getDate())
                .build();
    }

    public List<Standings> transformToStandings(List<StandingsDto> standingsDtos) {
        return standingsDtos.stream().map(this::transformToStandingRecord).collect(Collectors.toList());
    }

    private Standings transformToStandingRecord(StandingsDto standingsDto) {
        return Standings.builder()
                .team(Team.builder().fullName(standingsDto.getTeamName()).build())
                .teamPosition(standingsDto.getPosition())
                .wins(standingsDto.getTeamRecord().getWin())
                .losses(standingsDto.getTeamRecord().getLosses())
                .conferenceWins(standingsDto.getConferenceRecord().getWin())
                .conferenceLosses(standingsDto.getConferenceRecord().getLosses())
                .divisionWins(standingsDto.getDivisionRecord().getWin())
                .divisionLosses(standingsDto.getDivisionRecord().getLosses())
                .winPercentage(standingsDto.getStreak().getWinPercentage())
                .lossPercentage(standingsDto.getStreak().getLossPercentage())
                .streak(standingsDto.getStreak().getStreak())
                .date(standingsDto.getDate())
                .winStreak(standingsDto.getStreak().getWinStreak())
                .build();
    }
}
