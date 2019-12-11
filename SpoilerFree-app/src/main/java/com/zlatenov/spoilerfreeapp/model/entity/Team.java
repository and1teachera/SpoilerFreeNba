package com.zlatenov.spoilerfreeapp.model.entity;

/**
 * @author Angel Zlatenov
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

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
}
