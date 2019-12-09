package com.zlatenov.userauthorisationservice.model;

import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * @author Angel Zlatenov
 */
//@Entity
public class Privilege {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Roles> roles;
}