package com.zlatenov.spoilerfreeapp.model.service;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TeamServiceModel {

    private String fullName;
    private String shortName;
    private String nickName;
    private String conference;
    private String division;

}
