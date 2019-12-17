package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.PlayerServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.TeamServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface TeamService {

    void fetchAllTeams() throws UnresponsiveAPIException;

    List<TeamServiceModel> getAllTeams();

    List<PlayerServiceModel> getPlayersByTeamName(String teamName);

    void addRemoveFromWatched(String teamName);

    void addRemoveFromFavorite(String teamName);
}
