package com.api.realestate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
@SQLDelete(sql = "UPDATE PERSON SET DELETED = 1 WHERE ID = ?")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer personRole;

    public Person() {
    }

    public Person(Long id, String name, String email, String password, Integer personRole) {
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

    @JsonIgnore
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
