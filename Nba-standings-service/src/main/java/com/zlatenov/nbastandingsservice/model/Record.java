package com.zlatenov.nbastandingsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Record {

    private Short win;
    private Short loss;
}
