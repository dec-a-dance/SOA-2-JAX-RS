package com.soa.jax.dto;

import com.soa.jax.entity.inner.Address;
import com.soa.jax.entity.inner.VenueType;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VenueDTO {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String venueName; //Поле не может быть null, Строка не может быть пустой
    private Long venueCapacity; //Поле не может быть null, Значение поля должно быть больше 0
    private String venueType; //Поле может быть null
    private Address address; //Поле может быть null
}
