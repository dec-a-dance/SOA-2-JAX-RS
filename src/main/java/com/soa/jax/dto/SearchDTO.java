package com.soa.jax.dto;

import com.soa.jax.entity.inner.TicketType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchDTO {
    private String sort;
    private Boolean sortAsc;
    private TicketDTO filter;
}
