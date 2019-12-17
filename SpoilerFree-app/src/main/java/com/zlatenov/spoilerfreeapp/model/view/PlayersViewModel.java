package com.zlatenov.spoilerfreeapp.model.view;

import lombok.*;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlayersViewModel {

    List<PlayerViewModel> players;

}
