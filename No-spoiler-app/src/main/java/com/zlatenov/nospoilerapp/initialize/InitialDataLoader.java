package com.zlatenov.nospoilerapp.initialize;

import com.zlatenov.nospoilerapp.service.GameService;
import com.zlatenov.nospoilerapp.service.TeamService;
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

    private final GameService gamesService;
    private final TeamService teamsService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        teamsService.fetchAllTeams();
        gamesService.fetchAllGames();
    }
}
