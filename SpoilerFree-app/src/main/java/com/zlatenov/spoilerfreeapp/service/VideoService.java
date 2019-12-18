package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface VideoService {

    List<VideoServiceModel> getVideosForGame(GameServiceModel gameServiceModel);

    List<VideoServiceModel> getVideosForDate(Date date);

    void removeVideo(VideoServiceModel videoServiceModel);

    void addVideo(VideoServiceModel videoServiceModel);
}