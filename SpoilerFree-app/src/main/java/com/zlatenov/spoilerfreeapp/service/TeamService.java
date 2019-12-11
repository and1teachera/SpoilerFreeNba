package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.view.TeamViewModel;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface TeamService {

    void fetchAllTeams();

    List<TeamViewModel> getAllTeams();
}
