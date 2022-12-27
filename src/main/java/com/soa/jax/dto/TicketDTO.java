package com.soa.jax.dto;

import com.soa.jax.entity.Person;
import com.soa.jax.entity.Venue;
import com.soa.jax.entity.inner.Coordinates;
import com.soa.jax.entity.inner.TicketType;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TicketDTO {
    private Long id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates;
    private Person owner;
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Значение поля должно быть больше 0
    private Integer discount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private String comment; //Поле не может быть null
    private String type; //Поле может быть null
    private VenueDTO venue; //Поле может быть null
}
