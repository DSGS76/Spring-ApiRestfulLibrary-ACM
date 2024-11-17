package com.acm.apirestful.presentation.dto.libro;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LibroDTO(
        @JsonProperty("title") String titulo,
        @JsonProperty("first_publish_date")  String fechaPublicacion,
        @JsonProperty("description") String descripcion){
}
