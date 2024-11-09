package com.acm.apirestful.presentation.controller;

import com.acm.apirestful.presentation.dto.LibraryDTO;
import com.acm.apirestful.service.LibroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libro")
@RequiredArgsConstructor
@Slf4j
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/buscarPorAutor")
    public List<LibraryDTO> buscarLibroPorAutor(@RequestParam String nombreAutor) {
        return libroService.searchLibrosByAutor(nombreAutor);
    }

    @GetMapping("/buscarPorCategoria")
    public List<LibraryDTO> buscarLibroPorCategoria(@RequestParam String nombreCategoria) {
        return libroService.searchLibrosByCategoria(nombreCategoria);
    }

    @GetMapping("/buscarPorTitulo")
    public List<LibraryDTO> buscarLibroPorTitulo(@RequestParam String titulo) {
        return libroService.searchLibrosByTitulo(titulo);
    }

    @GetMapping("/eliminarPorTitulo")
    public void eliminarLibroPorTitulo(@RequestParam String titulo) {
        libroService.eliminarLibroByTitulo(titulo);
    }

}
