package com.zlatenov.gamesinformationservice.model.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.ZonedDateTime;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameServiceModel {

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String arena;
    private String city;

    private String homeTeamFullName;
    private String awayTeamFullName;
    private Score score;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof GameServiceModel))
            return false;

        GameServiceModel that = (GameServiceModel) o;

        return new EqualsBuilder().append(startTime, that.startTime)
                .append(arena, that.arena)
                .append(homeTeamFullName, that.homeTeamFullName)
                .append(awayTeamFullName, that.awayTeamFullName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(startTime)
                .append(arena)
                .append(homeTeamFullName)
                .append(awayTeamFullName)
                .toHashCode();
    }
}
