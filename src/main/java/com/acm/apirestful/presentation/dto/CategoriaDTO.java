package com.acm.apirestful.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriaDTO(
        @JsonProperty("name") String nombreCategoria) {
}
