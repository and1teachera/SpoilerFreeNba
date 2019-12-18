package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.entity.Video;
import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreeapp.model.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.repository.VideoRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.video.VideoDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.video.VideosDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;
    private VideoModelTransformer videoModelTransformer;
    private GamesRepository gamesRepository;
    private GamesModelTransformer gamesModelTransformer;
    private TeamRepository teamRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public List<VideoServiceModel> getVideosForGame(GameServiceModel gameServiceModel) {
        return videoModelTransformer.transformToServiceModels(videoRepository.findByGame(gamesRepository.findByHomeTeamAndAwayTeamAndStartTimeUtc(
                teamRepository.findByFullName(gameServiceModel.getHomeTeam().getFullName()),
                teamRepository.findByFullName(gameServiceModel.getAwayTeam().getFullName()),
                gameServiceModel.getDate())));
    }

    @Override
    public List<VideoServiceModel> getVideosForDate(Date date) {
        return videoModelTransformer.transformToServiceModels(
                videoRepository.findByGameIn(gamesRepository.findByStartTimeUtc(date)));
    }


    @Override
    public void removeVideo(VideoServiceModel videoServiceModel) {
        videoRepository.deleteByVideoId(videoServiceModel.getVideoId());
    }

    @Override
    public void addVideo(VideoServiceModel videoServiceModel) {
        GameServiceModel gameServiceModel = videoServiceModel.getGame();

        Team homeTeam = teamRepository.findByFullName(gameServiceModel.getHomeTeam().getFullName());
        Team awayTeam = teamRepository.findByFullName(gameServiceModel.getAwayTeam().getFullName());

        Game game = gamesRepository.findByHomeTeamAndAwayTeamAndStartTimeUtc(homeTeam, awayTeam, gameServiceModel.getDate());
        Video video = videoModelTransformer.transformToVideo(videoServiceModel);
        video.setGame(game);
        videoRepository.saveAndFlush(video);
    }

    @Override
    public void fetchVideos() throws UnresponsiveAPIException {
        List<Game> games = gamesRepository.findAllByStartTimeUtcBefore(DateUtil.getCurrentDateWithoutTime());
        VideosDto videosDto = webClientBuilder.build()
                .post()
                .uri("localhost:8084/videos")
                .body(Mono.just(GamesDto.builder()
                        .gameDtos(gamesModelTransformer.transformToGameDtos(games))
                        .build()), GamesDto.class)
                .retrieve()
                .bodyToMono(VideosDto.class)
                .block();

        if (videosDto == null) {
            throw new UnresponsiveAPIException();
        }

        saveVideos(videosDto.getVideoList());

    }

    private void saveVideos(List<VideoDto> videoList) {
        List<Video> videos = new ArrayList<>();
        Map<String, List<Team>> teamToNameMap =
                teamRepository.findAll().stream().collect(groupingBy(Team::getFullName));
        for (VideoDto videoDto : videoList) {
            Video video = videoModelTransformer.transformToVideo(videoDto);
            Team homeTeam = teamToNameMap.get(videoDto.getHomeTeamName()).get(0);
            Team awayTeam = teamToNameMap.get(videoDto.getAwayTeamName()).get(0);
            Game game = gamesRepository.findByHomeTeamAndAwayTeamAndStartTimeUtc(
                    homeTeam, awayTeam, DateUtil.parseDate(videoDto.getDate()));
            video.setGame(game);
            videos.add(video);
        }
        videoRepository.saveAll(videos);
    }
}
