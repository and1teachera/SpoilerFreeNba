package com.zlatenov.nbastandingsservice.transformer;

import com.zlatenov.nbastandingsservice.model.Record;
import com.zlatenov.nbastandingsservice.model.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.Streak;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
                .teamName(standingsResponseModel.getName())
                .conference(standingsResponseModel.getConference().getName())
                .division(standingsResponseModel.getDivision().getName())
                .teamRecord(Record.builder()
                                    .win(Short.valueOf(standingsResponseModel.getWin()))
                                    .loss(Short.valueOf(standingsResponseModel.getLoss()))
                                    .build())
                .conferenceRecord(Record.builder()
                                          .win(Short.valueOf(standingsResponseModel.getConference().getWin()))
                                          .loss(Short.valueOf(standingsResponseModel.getConference().getLoss()))
                                          .build())
                .divisionRecord(Record.builder()
                                        .win(Short.valueOf(standingsResponseModel.getDivision().getWin()))
                                        .loss(Short.valueOf(standingsResponseModel.getDivision().getLoss()))
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
}
