package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.StandingsServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.io.IOException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface StandingsService {

    void initializeDatabase() throws IOException, UnresponsiveAPIException;

    List<StandingsServiceModel> initStandingsData() throws IOException, UnresponsiveAPIException;

    //void fetchStandings() throws IOException, UnresponsiveAPIException;
}
