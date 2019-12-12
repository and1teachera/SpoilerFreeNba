package com.zlatenov.teamsinformationservice.model;

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

    private String shortName;
    private String fullName;
    private String nickname;
    private String logo;
    private String city;
    private Leagues leagues;
	private Integer nbaFranchise;

}
