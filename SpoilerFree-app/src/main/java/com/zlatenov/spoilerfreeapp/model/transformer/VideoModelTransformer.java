package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.binding.VideoBindingModel;
import com.zlatenov.spoilerfreeapp.model.entity.Video;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class VideoModelTransformer {

    private final ModelMapper modelMapper;
    private final GamesModelTransformer gamesModelTransformer;

    public VideoServiceModel transformToServiceModel(VideoViewModel video) {
        return VideoServiceModel.builder()
                .videoId(video.getVideoId())
                .game(gamesModelTransformer.transformToServiceModel(video.getGame()))
                .name(video.getName())
                .build();
    }

    public VideoViewModel transformToViewModel(VideoServiceModel video) {
        return VideoViewModel.builder()
                .game(gamesModelTransformer.transformToGameViewModel(video.getGame()))
                .videoId(video.getVideoId())
                .name(video.getName())
                //.isFavorite(video.is)
                .build();
    }

    public List<VideoViewModel> transformToViewModels(List<VideoServiceModel> videos) {
        return videos.stream().map(this::transformToViewModel).collect(Collectors.toList());
    }

    public VideoServiceModel transformToServiceModel(VideoBindingModel video) {
        return VideoServiceModel.builder()
                .videoId(video.getVideoId())
                //.game(gamesModelTransformer.transformToServiceModel(video.get()))
                //.name(video.getName())
                .build();
    }

    public List<VideoServiceModel> transformToServiceModels(List<Video> videos) {
        return videos.stream().map(this::transformToServiceModel).collect(Collectors.toList());
    }

    public VideoServiceModel transformToServiceModel(Video video) {
        return VideoServiceModel.builder()
                .name(video.getName())
                .game(gamesModelTransformer.transformToServiceModel(video.getGame()))
                .videoId(video.getVideoId())
                .build();
    }

    public Video transformToVideo(VideoServiceModel videoServiceModel) {
        return Video.builder()
                .videoId(videoServiceModel.getVideoId())
                .name(videoServiceModel.getName())
                .build();
    }
}
