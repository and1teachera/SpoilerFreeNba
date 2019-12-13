package com.zlatenov.gamesinformationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseModel {

    private String fullName;
	private Integer nbaFranchise;
    private Score score;

}
