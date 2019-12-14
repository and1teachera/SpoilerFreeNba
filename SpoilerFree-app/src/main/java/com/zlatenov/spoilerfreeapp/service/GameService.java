package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GameService {

    void fetchAllGames() throws UnresponsiveAPIException;

    List<GameViewModel> getAllGames();

}
