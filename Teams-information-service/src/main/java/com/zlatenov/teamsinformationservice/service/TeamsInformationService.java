package com.zlatenov.teamsinformationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.teamsinformationservice.model.service.TeamServiceModel;

import java.io.IOException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface TeamsInformationService {

    List<TeamServiceModel> getAllTeams();

    List<TeamServiceModel> initTeamsData() throws IOException, UnresponsiveAPIException;

    void initializeTeamsDatabase() throws IOException, UnresponsiveAPIException;
}
