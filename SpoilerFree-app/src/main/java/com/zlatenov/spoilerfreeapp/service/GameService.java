package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GameService {

    void fetchAllGames() throws UnresponsiveAPIException;

    List<GameServiceModel> getAllGames();

    List<GameServiceModel> getGamesForDate(Date date);

    List<Date> createDaysNavigationList(Date date);

    List<GameServiceModel> getGameInformation(String gameName, String date);

    List<GameServiceModel> getGamesForTeam(String teamName);
}
