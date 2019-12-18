package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface StandingsService {

    void fetchCurrentStandings() throws UnresponsiveAPIException;

    List<StandingsServiceModel> getStandingsForDate(Date date);

    List<StandingsServiceModel> getStandingsInformation(String gameName, String date);

    List<StandingsServiceModel> getCurrentStandings();

    StandingsServiceModel getStandingsForTeam(String teamName);
}
