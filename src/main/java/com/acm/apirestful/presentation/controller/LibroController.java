package com.acm.apirestful.presentation.controller;

import com.acm.apirestful.presentation.dto.ApiResponseDTO;
import com.acm.apirestful.presentation.dto.libro.LibraryDTO;
import com.acm.apirestful.services.LibroService;
import com.acm.apirestful.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Global.API_BASE_PATH
                + Constants.Global.API_VERSION
                + Constants.Libro.LIBRO_SERVICE_PATH)
@RequiredArgsConstructor
@Slf4j
public class LibroController {

    private final LibroService libroService;

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_AUTHOR)
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public ResponseEntity<?> buscarLibroPorAutor(@RequestParam String nombreAutor) {
        ApiResponseDTO<List<LibraryDTO>> response = libroService.searchLibrosByAutor(nombreAutor);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_SUBJECT)
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public ResponseEntity<?> buscarLibroPorCategoria(@RequestParam String nombreCategoria) {
        ApiResponseDTO<List<LibraryDTO>> response = libroService.searchLibrosByCategoria(nombreCategoria);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_TITLE)
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public ResponseEntity<?> buscarLibroPorTitulo(@RequestParam String titulo) {
        ApiResponseDTO<List<LibraryDTO>> response = libroService.searchLibrosByTitulo(titulo);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_LENDING)
    @PreAuthorize("""
                    hasAnyRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN,
                                T(com.acm.apirestful.utils.Constants$User).USER_ROLE_CLIENT)
                    """)
    public ResponseEntity<?> buscarLibroPorPrestamo(@RequestParam Short idPrestamo,
                                                   @RequestParam Short idCliente,
                                                   Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            ApiResponseDTO<List<LibraryDTO>> response = libroService.searchLibrosByPrestamo(idPrestamo, idCliente);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
        } else if (role.equals("ROLE_CLIENT")) {
            ApiResponseDTO<List<LibraryDTO>> response = libroService.searchLibrosByPrestamo(idPrestamo, idCliente, username);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
        }
        ApiResponseDTO<Collections> response = new ApiResponseDTO<>();
        response.BadOperation();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_UPDATE)
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public ResponseEntity<?> actualizarLibroPorTitulo(@RequestParam LibraryDTO libraryDTO, @RequestParam String titulo) {
        ApiResponseDTO<LibraryDTO> response = libroService.actualizarLibroByTitulo(libraryDTO, titulo);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_DELETE)
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public ResponseEntity<?> eliminarLibroPorTitulo(@RequestParam String titulo) {
        ApiResponseDTO<LibraryDTO> response = libroService.eliminarLibroByTitulo(titulo);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping(Constants.Libro.LIBRO_SERVICE_PATH_SAVE)
    @PreAuthorize("hasRole(T(com.acm.apirestful.utils.Constants$User).USER_ROLE_ADMIN)")
    public ResponseEntity<?> guardarLibro(@RequestParam LibraryDTO libraryDTO) {
        ApiResponseDTO<LibraryDTO> response = libroService.guardarLibro(libraryDTO);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

}
