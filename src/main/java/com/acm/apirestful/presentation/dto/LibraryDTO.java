package com.acm.apirestful.presentation.dto;

public record LibraryDTO(LibroDTO libro,
                         CategoriaDTO categoria,
                         AutorDTO autor) {
}
