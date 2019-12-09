package com.zlatenov.nospoilerapp.service;

import com.zlatenov.nospoilerapp.model.view.TeamViewModel;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface TeamService {

    void fetchAllTeams();

    List<TeamViewModel> getAllTeams();
}
