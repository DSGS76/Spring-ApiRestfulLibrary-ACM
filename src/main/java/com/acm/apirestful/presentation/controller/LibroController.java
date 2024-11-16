package com.acm.apirestful.presentation.controller;

import com.acm.apirestful.persistence.entity.Libro;
import com.acm.apirestful.presentation.dto.LibraryDTO;
import com.acm.apirestful.services.LibroService;
import com.acm.apirestful.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Global.API_BASE_PATH + Constants.Global.API_VERSION + Constants.Libro.LIBRO_SERVICE_PATH)
@RequiredArgsConstructor
@Slf4j
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/buscarPorAutor")
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public List<LibraryDTO> buscarLibroPorAutor(@RequestParam String nombreAutor) {
        return libroService.searchLibrosByAutor(nombreAutor);
    }

    @GetMapping("/buscarPorCategoria")
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public List<LibraryDTO> buscarLibroPorCategoria(@RequestParam String nombreCategoria) {
        return libroService.searchLibrosByCategoria(nombreCategoria);
    }

    @GetMapping("/buscarPorTitulo")
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public List<LibraryDTO> buscarLibroPorTitulo(@RequestParam String titulo) {
        return libroService.searchLibrosByTitulo(titulo);
    }

    @GetMapping("/buscarPorPrestamo")
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public List<LibraryDTO> buscarLibroPorPrestamo(@RequestParam Short idPrestamo,
                                                   @RequestParam Short idCliente,
                                                   Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return libroService.searchLibrosByPrestamo(idPrestamo, idCliente);
        } else if (role.equals("ROLE_CLIENT")) {
            return libroService.searchLibrosByPrestamo(idPrestamo, idCliente, username);
        }
        return Collections.emptyList();
    }

    @GetMapping("/actualizarPorTitulo")
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public void actualizarLibroPorTitulo(@RequestParam Libro libro, @RequestParam String titulo) {
        libroService.actualizarLibroByTitulo(libro, titulo);
    }

    @GetMapping("/eliminarPorTitulo")
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public void eliminarLibroPorTitulo(@RequestParam String titulo) {
        libroService.eliminarLibroByTitulo(titulo);
    }

    @GetMapping("/guardar")
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public void guardarLibro(@RequestParam Libro libro) {
        libroService.guardarLibro(libro);
    }

}
