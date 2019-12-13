package com.zlatenov.videoproviderservice.controller;

import com.zlatenov.spoilerfreesportsapi.model.dto.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.VideosDto;
import com.zlatenov.videoproviderservice.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Angel Zlatenov
 */

@RestController
@AllArgsConstructor
public class VideoProviderController {

    private VideoService videoService;

    @PostMapping(path = "/videos")
    private ResponseEntity login(GameDto gameDto) {
        VideosDto videosDto = videoService.getVideosForGame(gameDto);
        return ResponseEntity
                .ok(videosDto);
    }
}
