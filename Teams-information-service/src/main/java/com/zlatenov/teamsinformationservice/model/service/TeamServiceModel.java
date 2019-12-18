package com.zlatenov.teamsinformationservice.model.service;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamServiceModel {

    private String id;
    private String shortName;
    private String fullName;
    private String nickName;
    private String logo;
    private String city;
    private String confName;
    private String divName;

    private List<PlayerServiceModel> players;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof TeamServiceModel))
            return false;

        TeamServiceModel that = (TeamServiceModel) o;

        return new EqualsBuilder().append(fullName, that.fullName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fullName).toHashCode();
    }
}
