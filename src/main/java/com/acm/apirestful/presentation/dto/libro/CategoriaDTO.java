package com.acm.apirestful.presentation.dto.libro;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriaDTO(
        @JsonProperty("name") String nombreCategoria) {
}
