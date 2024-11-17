package com.acm.apirestful.presentation.dto.libro;

public record LibraryDTO(LibroDTO libro,
                         CategoriaDTO categoria,
                         AutorDTO autor) {
}
