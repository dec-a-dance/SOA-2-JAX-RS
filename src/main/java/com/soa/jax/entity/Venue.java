package com.soa.jax.entity;

import com.soa.jax.entity.inner.Address;
import com.soa.jax.entity.inner.VenueType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotEmpty
    private String venueName; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @Min(1)
    private Long venueCapacity; //Поле не может быть null, Значение поля должно быть больше 0
    @NotNull
    private VenueType venueType; //Поле может быть null
    @NotNull
    @OneToOne
    private Address address; //Поле может быть null
}