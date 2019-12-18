package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Standings;
import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.DayStandingsModel;
import com.zlatenov.spoilerfreeapp.model.view.StandingsViewModel;
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
                .index(standings.getIndex())
                .conferenceIndex(standings.getIndex())
                .divisionIndex(standings.getIndex())
                .record(standings.getRecord())
                .conferenceRecord(standings.getConferenceRecord())
                .divisionRecord(standings.getDivisionRecord())
                .winPercentage(standings.getWinPercentage())
                .lossPercentage(standings.getLossPercentage())
                .streak(Streak.builder().streak(standings.getStreak()).winStreak(standings.getWinStreak()).build())
                .date(standings.getDate())
                .build();
    }
}
