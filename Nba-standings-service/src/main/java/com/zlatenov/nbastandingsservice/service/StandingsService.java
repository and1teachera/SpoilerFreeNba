package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface StandingsService {

    void initializeDatabase() throws IOException, UnresponsiveAPIException;

    List<StandingsServiceModel> initStandingsData() throws IOException, UnresponsiveAPIException;

    void calculateStandings() throws ParseException, UnresponsiveAPIException;

    //void fetchStandings() throws IOException, UnresponsiveAPIException;
}
