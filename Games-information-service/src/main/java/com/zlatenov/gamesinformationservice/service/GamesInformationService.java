package com.zlatenov.gamesinformationservice.service;

import com.zlatenov.gamesinformationservice.model.service.GameServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GamesInformationService {

    List<GameServiceModel> getAllGames();

    List<GameServiceModel> getGamesForDate(Date date);

    List<GameServiceModel> initGamesData() throws IOException, UnresponsiveAPIException;

    void fetchGamesFromApi() throws IOException, UnresponsiveAPIException;
}
