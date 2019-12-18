package com.zlatenov.videoproviderservice.controller;

import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.video.VideosDto;
import com.zlatenov.videoproviderservice.model.Video;
import com.zlatenov.videoproviderservice.service.VideoService;
import com.zlatenov.videoproviderservice.transformer.ModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@RestController
@AllArgsConstructor
public class VideoProviderController {

    private VideoService videoService;
    private ModelTransformer modelTransformer;

    @PostMapping(path = "/game/videos")
    private ResponseEntity gameVideos(GameDto gameDto) {
        List<Video> videosForGame = videoService.getVideosForGame(gameDto);
        VideosDto videosDto = VideosDto.builder().videoList(modelTransformer.transformVideosToDtoList(videosForGame)).build();
        return ResponseEntity
                .ok(videosDto);
    }

    @PostMapping(path = "/videos")
    private ResponseEntity videos(GamesDto gamesDto) {
        List<Video> videos = videoService.getVideos(gamesDto);
        VideosDto videosDto = VideosDto.builder().videoList(modelTransformer.transformVideosToDtoList(videos)).build();
        return ResponseEntity
                .ok(videosDto);
    }
}
