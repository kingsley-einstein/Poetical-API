package com.poetical.api.models;

import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.CascadeType;

@Entity
@Table(name = "roles")
public class Role implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User holder;

    protected Role(){}

    public Role(String name, User holder) {
        this.name = name;
        this.holder = holder;
    }

    public String getName() {
        return name;
    }
}