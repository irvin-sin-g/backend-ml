package com.ifmd.mercaditolibre.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {

    @JsonProperty("idVenta")
    private Long idVenta;

    @JsonProperty("moneda")
    private String moneda;
}