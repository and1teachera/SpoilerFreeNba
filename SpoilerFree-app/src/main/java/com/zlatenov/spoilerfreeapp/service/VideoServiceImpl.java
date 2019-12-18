package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.entity.Video;
import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.repository.UserRepository;
import com.zlatenov.spoilerfreeapp.repository.VideoRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;
    private VideoModelTransformer videoModelTransformer;
    private GamesRepository gamesRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;

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
}
