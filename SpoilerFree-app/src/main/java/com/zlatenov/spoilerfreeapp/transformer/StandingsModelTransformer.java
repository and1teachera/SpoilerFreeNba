package com.zlatenov.spoilerfreeapp.transformer;

import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.DayStandingsModel;
import com.zlatenov.spoilerfreeapp.model.view.StandingsViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Component
public class StandingsModelTransformer {

    public List<StandingsViewModel> transformServiceModelsToViews(List<StandingsServiceModel> standingsForDate) {
        return null;
    }

    public List<DayStandingsModel> transformServiceModelsToDayViews(List<StandingsServiceModel> standingsInformation) {
        return null;
    }

    public StandingsViewModel transformServiceModelToView(StandingsServiceModel standingsForTeam) {
        return null;
    }
}
