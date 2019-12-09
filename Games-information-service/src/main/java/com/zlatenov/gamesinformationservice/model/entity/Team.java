package com.zlatenov.gamesinformationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Team extends BaseEntity {

    private String shortName;
    private String fullName;
    private String nickName;
    private String logo;
    private String city;
    private String confName;
    private String divName;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Team))
            return false;

        Team team = (Team) o;

        return new EqualsBuilder().append(fullName, team.fullName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fullName).toHashCode();
    }
}
