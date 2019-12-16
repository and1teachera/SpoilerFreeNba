package com.zlatenov.spoilerfreeapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Game extends BaseEntity {

    private Date startTimeUtc;
    private Date endTimeUtc;
    private String arena;
    private String city;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "homeTeam_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "awayTeam_id", nullable = false)
    private Team awayTeam;
    private Short homeTeamScore;
    private Short awayTeamScore;

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private List<Video> videos;

}
