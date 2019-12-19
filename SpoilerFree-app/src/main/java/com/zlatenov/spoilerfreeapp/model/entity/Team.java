package com.zlatenov.spoilerfreeapp.model.entity;

/**
 * @author Angel Zlatenov
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Team extends BaseEntity {

    private String shortName;
    private String nickName;
    private String fullName;
    private String logo;
    private String city;
    private String confName;
    private String divName;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> players;

}
