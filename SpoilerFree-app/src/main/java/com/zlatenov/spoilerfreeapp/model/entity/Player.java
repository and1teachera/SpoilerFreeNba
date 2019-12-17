package com.zlatenov.spoilerfreeapp.model.entity;

import lombok.*;

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
public class Player extends BaseEntity {

    private String name;
}
