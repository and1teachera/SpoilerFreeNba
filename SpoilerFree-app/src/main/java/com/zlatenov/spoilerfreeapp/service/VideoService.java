package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface VideoService {

    void addRemoveFromFavorites(VideoServiceModel videoServiceModel, String name);

    List<VideoServiceModel> getVideosForGame(GameServiceModel gameServiceModel);

    List<VideoServiceModel> getVideosForDate(Date date);

    List<VideoServiceModel> getFavourites(String username);

    void removeFavoriteVideo(VideoServiceModel transformToServiceModel);

    void removeVideo(VideoServiceModel transformToServiceModel);

    void addVideo(VideoServiceModel transformToServiceModel);
}
