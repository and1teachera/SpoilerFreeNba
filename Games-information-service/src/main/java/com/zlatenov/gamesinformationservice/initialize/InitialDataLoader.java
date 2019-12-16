package com.zlatenov.gamesinformationservice.initialize;

import com.zlatenov.gamesinformationservice.service.GamesInformationService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final GamesInformationService gamesInformationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (CollectionUtils.isEmpty(gamesInformationService.getAllGames())) {
            try {
                gamesInformationService.initializeDatabase();
            }
            catch (IOException | UnresponsiveAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
