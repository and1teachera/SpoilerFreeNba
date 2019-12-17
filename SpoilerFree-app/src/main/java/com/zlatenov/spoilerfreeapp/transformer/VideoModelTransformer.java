package com.zlatenov.spoilerfreeapp.transformer;

import com.zlatenov.spoilerfreeapp.model.binding.VideoBindingModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Component
public class VideoModelTransformer {

    public VideoServiceModel transformToServiceModel(VideoViewModel video) {
        return null;
    }

    public List<VideoViewModel> transformToViewModels(List<VideoServiceModel> videos) {
        return null;
    }

    public VideoServiceModel transformToServiceModel(VideoBindingModel videoBindingModel) {
        return null;
    }
}
