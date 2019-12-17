package com.zlatenov.spoilerfreeapp.model.view;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VideoViewModel {

    private String videoId;
    private String name;
    private GameViewModel game;
    private boolean isFavorite;
}
