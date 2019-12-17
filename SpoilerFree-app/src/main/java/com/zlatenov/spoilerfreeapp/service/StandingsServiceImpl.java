package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public class StandingsServiceImpl implements StandingsService {
    @Override
    public void fetchAllStandings() throws UnresponsiveAPIException {

    }

    @Override
    public List<StandingsServiceModel> getStandingsForDate(Date date) {
        return null;
    }

    @Override
    public List<StandingsServiceModel> fetchCurrentStandings() {
        return null;
    }

    @Override
    public List<StandingsServiceModel> getStandingsInformation(String gameName, String date) {
        return null;
    }

    @Override
    public List<StandingsServiceModel> getCurrentStandings() {
        return null;
    }

    @Override
    public StandingsServiceModel getStandingsForTeam(String teamName) {
        return null;
    }
}
