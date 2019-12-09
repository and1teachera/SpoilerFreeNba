package com.zlatenov.nospoilerapp.service;

import com.zlatenov.nospoilerapp.model.view.GameViewModel;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GameService {

    void fetchAllGames();

    List<GameViewModel> getAllGames();

}
