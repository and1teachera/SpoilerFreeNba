package com.zlatenov.spoilerfreeapp.cron;

import com.zlatenov.spoilerfreeapp.service.GameService;
import com.zlatenov.spoilerfreeapp.service.StandingsService;
import com.zlatenov.spoilerfreeapp.service.VideoService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class CronJob {

    private final GameService gamesService;
    private final StandingsService standingsService;
    private final VideoService videoService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void fetchGamesForToday() {
        try {
            gamesService.fetchGamesForDate(DateUtil.getCurrentDateWithoutTime());
        } catch (UnresponsiveAPIException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 15 9 * * ?")
    public void fetchStandingsForToday() {
        try {
            standingsService.fetchCurrentStandings();

        } catch (UnresponsiveAPIException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 30 9 * * ?")
    public void fetchVideosForTodayGames() {
        try {
            videoService.fetchVideosForTodayGames();

        } catch (UnresponsiveAPIException e) {
            e.printStackTrace();
        }
    }
}
