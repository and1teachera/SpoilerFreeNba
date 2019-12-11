package com.zlatenov.nospoilersportsapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideosDto {

    List<Video> videoList;
}
