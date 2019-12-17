package com.zlatenov.spoilerfreeapp.model.service;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VideoServiceModel {

    private String videoId;
    private String name;
    private GameServiceModel game;
}
