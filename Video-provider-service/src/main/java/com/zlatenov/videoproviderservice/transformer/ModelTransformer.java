package com.zlatenov.videoproviderservice.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.video.VideoDto;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import com.zlatenov.videoproviderservice.model.Game;
import com.zlatenov.videoproviderservice.model.Video;
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
public class ModelTransformer {

    private final ModelMapper modelMapper;

    public Game transformDtoToGame(GameDto gameDto) {
        return Game.builder()
                .homeTeamName(gameDto.getHomeTeamName())
                .awayTeamName(gameDto.getAwayTeamName())
                .date(DateUtil.parseDate(gameDto.getDate()))
                .build();
    }


    public List<VideoDto> transformVideosToDtoList(List<Video> videoList) {
        return videoList.stream().map(this::transformToVideoDto).collect(Collectors.toList());
    }

    public VideoDto transformToVideoDto(Video video) {
        return VideoDto.builder()
                .homeTeamName(video.getHomeTeamName())
                .awayTeamName(video.getAwayTeamName())
                .duration(video.getDuration())
                .id(video.getId())
                .build();
    }
}
