package com.zlatenov.nbastandingsservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Angel Zlatenov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DivisionResponseModel {

    private String name;
    private String rank;
    private String win;
    private String loss;
    private String GamesBehind;

}
