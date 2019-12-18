package com.zlatenov.spoilerfreeapp.model.view;

import lombok.*;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TeamViewModel {

    private String fullName;
    private String shortName;
    private String nickName;
    private String conference;
    private String division;
    private String logo;
    private String city;
    private boolean isWatched;
    private boolean isFavorite;

}
