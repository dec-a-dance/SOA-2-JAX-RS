package com.soa.jax.entity;

import com.soa.jax.entity.inner.Coordinates;
import com.soa.jax.entity.inner.TicketType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Min(1)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @ManyToOne
    private Coordinates coordinates; //Поле не может быть null
    @ManyToOne
    private Person owner;
    @NotNull
    private LocalDate creationDate = LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Min(1)
    private float price; //Значение поля должно быть больше 0
    @NotNull
    @Min(1)
    @Max(100)
    private Integer discount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    @NotNull
    private String comment; //Поле не может быть null
    private TicketType type; //Поле может быть null
    @ManyToOne
    private Venue venue; //Поле может быть null
}
