package com.acm.apirestful.presentation.dto.libro;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AutorDTO(
        @JsonProperty("name") String nombre,
        @JsonProperty("bio")  String biografia
) {
}
