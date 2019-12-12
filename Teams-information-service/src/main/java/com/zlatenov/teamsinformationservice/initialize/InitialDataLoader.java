package com.zlatenov.teamsinformationservice.initialize;

import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.teamsinformationservice.service.TeamsInformationService;
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

    private final TeamsInformationService teamsInformationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            teamsInformationService.initializeDatabase();
        }
        catch (IOException | UnresponsiveAPIException e) {
            e.printStackTrace();
        }
    }
}
