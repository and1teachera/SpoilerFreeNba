package com.zlatenov.nbastandingsservice.initialize;

import com.zlatenov.nbastandingsservice.service.StandingsService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
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

    private final StandingsService standingsService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            standingsService.initializeDatabase();
            //standingsService.calculateStandings();
        }
        catch (IOException | UnresponsiveAPIException  e) {
            e.printStackTrace();
        }
    }
}
