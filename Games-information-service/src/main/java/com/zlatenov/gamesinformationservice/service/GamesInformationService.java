package com.zlatenov.gamesinformationservice.service;

import com.zlatenov.gamesinformationservice.model.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.TeamServiceModel;
import com.zlatenov.nospoilersportsapi.model.exception.UnresponsiveAPIException;

import java.io.IOException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GamesInformationService {

    List<GameServiceModel> getAllGames() throws IOException, UnresponsiveAPIException;

    List<GameServiceModel> initGamesData() throws IOException, UnresponsiveAPIException;

    List<TeamServiceModel> initTeamsData() throws IOException, UnresponsiveAPIException;

    void initializeDatabase() throws IOException, UnresponsiveAPIException;
}
