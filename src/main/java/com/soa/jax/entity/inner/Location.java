package com.soa.jax.entity.inner;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long locationX;

    private Float locationY;
    @NotNull
    private String locationName; //Поле не может быть null
}
