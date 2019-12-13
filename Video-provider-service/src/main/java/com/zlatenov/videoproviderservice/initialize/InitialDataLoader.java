package com.zlatenov.videoproviderservice.initialize;

import com.zlatenov.videoproviderservice.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final VideoService videoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        GameDto gameDto = gameDto = GameDto.builder().homeTeamName("Detroit Pistons").awayTeamName("Dallas Mavericks").date(
//                Date.from(
//                Instant.now().minus(2, ChronoUnit.DAYS))).build();
//        VideosDto videosForGame = videoService.getVideosForGame(gameDto);
//
//        GameDto gameDto1 = gameDto = GameDto.builder().homeTeamName("Denver Nuggets").awayTeamName("Portland Trail Blazers").date(
//                Date.from(
//                        Instant.now().minus(2, ChronoUnit.DAYS))).build();
//        VideosDto videosForGame1 = videoService.getVideosForGame(gameDto1);
//
//        GameDto gameDto2 = gameDto = GameDto.builder().homeTeamName("Phaladelphia 76ers").awayTeamName("Boston Celtics").date(
//                Date.from(
//                        Instant.now().minus(2, ChronoUnit.DAYS))).build();
//        VideosDto videosForGame2 = videoService.getVideosForGame(gameDto2);
//        System.out.println("");
    }
}
