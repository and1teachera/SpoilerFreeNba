package com.zlatenov.nbastandingsservice.processor;

import com.zlatenov.nbastandingsservice.model.TeamResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamsResponse {

    List<TeamResponseModel> teams;
}
