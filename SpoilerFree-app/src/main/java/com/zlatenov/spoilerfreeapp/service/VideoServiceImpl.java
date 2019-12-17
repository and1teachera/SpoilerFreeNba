package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public class VideoServiceImpl implements VideoService {
    @Override
    public void addRemoveFromFavorites(VideoServiceModel videoServiceModel, String name) {

    }

    @Override
    public List<VideoServiceModel> getVideosForGame(GameServiceModel gameServiceModel) {
        return null;
    }

    @Override
    public List<VideoServiceModel> getVideosForDate(Date date) {
        return null;
    }

    @Override
    public List<VideoServiceModel> getFavourites(String username) {
        return null;
    }

    @Override
    public void removeFavoriteVideo(VideoServiceModel transformToServiceModel) {

    }

    @Override
    public void removeVideo(VideoServiceModel transformToServiceModel) {

    }

    @Override
    public void addVideo(VideoServiceModel transformToServiceModel) {

    }
}
