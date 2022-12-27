package com.soa.jax.entity.inner;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Min(-208)
    private Integer x; //Значение поля должно быть больше -208
    @Max(592)
    private Integer y; //Максимальное значение поля: 592
}
