package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GameService {

    void fetchAllGames();

    List<GameViewModel> getAllGames();

}
