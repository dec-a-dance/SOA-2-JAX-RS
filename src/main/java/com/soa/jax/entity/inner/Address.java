package com.soa.jax.entity.inner;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String street; //Поле не может быть null
    @NotNull
    private String zipCode; //Поле не может быть null
    @NotNull
    @OneToOne
    private Location town; //Поле не может быть null
}
