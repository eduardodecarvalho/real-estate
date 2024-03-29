package com.api.realestate.entity.dto;

import java.io.Serializable;

public class PersonDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer personRole;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String name, String email, String password, Integer personRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.personRole = personRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPersonRole() {
        return personRole;
    }

    public void setPersonRole(Integer personRole) {
        this.personRole = personRole;
    }
}
