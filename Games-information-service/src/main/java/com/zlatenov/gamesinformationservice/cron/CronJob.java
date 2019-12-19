package com.zlatenov.gamesinformationservice.cron;

import com.zlatenov.gamesinformationservice.service.GamesInformationService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class CronJob {

    private final GamesInformationService gamesInformationService;

    @Scheduled(cron = "0 30 8 * * ?")
    public void fetchGamesForToday() {
        try {
            gamesInformationService.fetchGamesFromApi();
        } catch (UnresponsiveAPIException | IOException e) {
            e.printStackTrace();
        }
    }

}
