package com.zlatenov.spoilerfreeapp.initialize;

import com.zlatenov.spoilerfreeapp.service.GameService;
import com.zlatenov.spoilerfreeapp.service.StandingsService;
import com.zlatenov.spoilerfreeapp.service.TeamService;
import com.zlatenov.spoilerfreeapp.service.VideoService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    private final StandingsService standingsService;
    private final VideoService videoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (CollectionUtils.isEmpty(teamsService.getAllTeams())) {
            try {
                teamsService.fetchAllTeams();
                gamesService.fetchAllGames();
                standingsService.fetchCurrentStandings();
                videoService.fetchVideos();
            }
            catch (UnresponsiveAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
