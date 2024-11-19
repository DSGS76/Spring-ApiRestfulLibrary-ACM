package com.acm.apirestful.services;

import com.acm.apirestful.persistence.entity.Autor;
import com.acm.apirestful.persistence.entity.Categoria;
import com.acm.apirestful.persistence.entity.Libro;
import com.acm.apirestful.persistence.repository.AutorRepository;
import com.acm.apirestful.persistence.repository.CategoriaRepository;
import com.acm.apirestful.persistence.repository.LibroRepository;
import com.acm.apirestful.persistence.repository.PrestamoRepository;
import com.acm.apirestful.presentation.dto.ApiResponseDTO;
import com.acm.apirestful.presentation.dto.libro.AutorDTO;
import com.acm.apirestful.presentation.dto.libro.CategoriaDTO;
import com.acm.apirestful.presentation.dto.libro.LibraryDTO;
import com.acm.apirestful.presentation.dto.libro.LibroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final ApiService apiService;
    private final PrestamoRepository prestamoRepository;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        CategoriaRepository categoriaRepository,
                        ApiService apiService,
                        PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.apiService = apiService;
        this.prestamoRepository = prestamoRepository;
    }

    public ApiResponseDTO<LibraryDTO> eliminarLibroByTitulo(String titulo) {
        ApiResponseDTO<LibraryDTO> response = new ApiResponseDTO<>();
        var libro = libroRepository.findLibroByTitulo(titulo);
        var resultado = libroRepository.deleteLibroByTitulo(titulo);
        if (resultado == 0) {
            response.FailedOperation();
            return response;
        }
        LibraryDTO dto = mapToDTO(libro);
        response.SuccessOperation(dto);
        return response;
    }

    public ApiResponseDTO<LibraryDTO> actualizarLibroByTitulo(LibraryDTO request, String titulo) {
        ApiResponseDTO<LibraryDTO> response = new ApiResponseDTO<>();
        var libro = libroRepository.findLibroByTitulo(titulo);
        if (libro == null) {
            response.FailedOperation();
            return response;
        }
        libroRepository.updateLibroByTitulo(libro, titulo);
        response.SuccessOperation(request);
        return response;
    }

    public ApiResponseDTO<LibraryDTO> guardarLibro(LibraryDTO request) {
        ApiResponseDTO<LibraryDTO> response = new ApiResponseDTO<>();
        var autor = autorRepository.findByNombre(request.autor().nombre());
        var categoria = categoriaRepository.findByNombreCategoria(request.categoria().nombreCategoria());
        if (autor.isEmpty() || categoria.isEmpty()) {
            response.FailedOperation();
            return response;
        }
        Libro libro = mapToEntity(request);
        libroRepository.save(libro);
        response.SuccessOperation(request);
        return response;
    }

    public ApiResponseDTO<List<LibraryDTO>> searchLibrosByPrestamo(Short idPrestamo, Short idCliente) {
        ApiResponseDTO<List<LibraryDTO>> response = new ApiResponseDTO<>();
        List<Libro> libroSearch = libroRepository.findByPrestamoAndCliente(idPrestamo, idCliente);
        if (libroSearch.isEmpty()) {
            response.FailedOperation();
            log.info("no se encontraron libros");
            return response;
        }
        List<LibraryDTO> libros = new ArrayList<>();
        for (Libro l: libroSearch) {
            LibraryDTO libro = mapToDTO(l);
            libros.add(libro);
        }
        response.SuccessOperation(libros);
        return response;
    }

    public ApiResponseDTO<List<LibraryDTO>> searchLibrosByPrestamo(Short idPrestamo, Short idCliente, String username) {
        ApiResponseDTO<List<LibraryDTO>> response = new ApiResponseDTO<>();
        boolean perteneceCliente = prestamoRepository.existsByIdAndClienteUserUsername(idPrestamo, username);

        if (perteneceCliente) {
            List<Libro> libroSearch = libroRepository.findByPrestamoAndCliente(idPrestamo, idCliente);
            List<LibraryDTO> libros = new ArrayList<>();
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            response.SuccessOperation(libros);
            return response;
        }
        response.FailedOperation();
        log.info("no se encontraron libros o pertenecientes al usuario");
        return response;
    }

    public ApiResponseDTO<List<LibraryDTO>> searchLibrosByCategoria(String nombreCategoria) {
        ApiResponseDTO<List<LibraryDTO>> response = new ApiResponseDTO<>();
        List<Libro> libroSearch = libroRepository.findLibrosByCategoriaNombreCategoriaLike(nombreCategoria);
        List<LibraryDTO> libros = new ArrayList<>();

        if (libroSearch.isEmpty()) {
            LibraryDTO libroDTO = apiService.searchLibraryByCategoria(nombreCategoria);
            if (libroDTO != null) {
                if (existeLibro(libroDTO)) {
                    libros.add(libroDTO);
                    log.info("Se encontraron libros ya guardados");
                } else {
                    guardarLibroDTO(libroDTO);
                    libros.add(libroDTO);
                    log.info("Se encontraron libros y fueron guardados.");
                }
                response.SuccessOperation(libros);
                return response;
            }
            response.FailedOperation();
            return response;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            response.SuccessOperation(libros);
            return response;
        }
    }

    public ApiResponseDTO<List<LibraryDTO>> searchLibrosByTitulo(String titulo) {
        ApiResponseDTO<List<LibraryDTO>> response = new ApiResponseDTO<>();
        List<Libro> libroSearch = libroRepository.findLibrosByTituloLike(titulo);
        List<LibraryDTO> libros = new ArrayList<>();

        if (libroSearch.isEmpty()) {
            LibraryDTO libroDTO = apiService.searchLibraryByTitulo(titulo);
            if (libroDTO != null) {
                if (existeLibro(libroDTO)) {
                    libros.add(libroDTO);
                    log.info("Se encontraron libros ya guardados");
                } else {
                    guardarLibroDTO(libroDTO);
                    libros.add(libroDTO);
                    log.info("Se encontraron libros y fueron guardados.");
                }
                response.SuccessOperation(libros);
                return response;
            }
            log.info("No se encontraron libros.");
            response.FailedOperation();
            return response;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            response.SuccessOperation(libros);
            return response;
        }
    }

    public ApiResponseDTO<List<LibraryDTO>> searchLibrosByAutor(String nombreAutor) {
        ApiResponseDTO<List<LibraryDTO>> response = new ApiResponseDTO<>();
        List<Libro> libroSearch = libroRepository.findLibrosByAutorNombreLike(nombreAutor);
        List<LibraryDTO> libros = new ArrayList<>();

        if (libroSearch.isEmpty()) {
            LibraryDTO libroDTO = apiService.searchLibraryByAutor(nombreAutor);
            if (libroDTO != null) {
                if (existeLibro(libroDTO)) {
                    libros.add(libroDTO);
                    log.info("Se encontraron libros ya guardados");
                } else {
                    guardarLibroDTO(libroDTO);
                    libros.add(libroDTO);
                    log.info("Se encontraron libros y fueron guardados.");
                }
                response.SuccessOperation(libros);
                return response;
            }
            log.info("No se encontraron libros.");
            response.FailedOperation();
            return response;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            response.SuccessOperation(libros);
            return response;
        }
    }

    private void guardarLibroDTO(LibraryDTO libroDTO) {
        Libro libro = mapToEntity(libroDTO);
        libroRepository.save(libro);
        log.info(libro.toString());
    }

    private boolean existeLibro(LibraryDTO libroDTO) {
        return libroRepository.existsLibroByTituloAndFechaPublicacionAndAutorNombreAndCategoriaNombreCategoria(
                libroDTO.libro().titulo(),
                libroDTO.libro().fechaPublicacion(),
                libroDTO.autor().nombre(),
                libroDTO.categoria().nombreCategoria()
        );
    }

    private LibraryDTO mapToDTO(Libro libro) {
        LibroDTO l = new LibroDTO(libro.getTitulo(), libro.getFechaPublicacion(), libro.getDescripcion());
        CategoriaDTO c = new CategoriaDTO(libro.getCategoria().getNombreCategoria());
        AutorDTO a = new AutorDTO(libro.getAutor().getNombre(), libro.getAutor().getBiografia());
        return new LibraryDTO(l, c, a);
    }

    private Libro mapToEntity(LibraryDTO libraryDTO) {
        // Truncar datos largos de ser necesario
        String descripcion = libraryDTO.libro().descripcion().substring(0,255);
        String biografia = libraryDTO.autor().biografia().substring(0,255);
        Libro l = new Libro(libraryDTO.libro().titulo(),
                            libraryDTO.libro().fechaPublicacion(),
                            true ,
                            descripcion);
        Autor a = new Autor(libraryDTO.autor().nombre(),
                            biografia);
        Categoria c = new Categoria(libraryDTO.categoria().nombreCategoria(),
                        "Descripción no proporcionada");
        l.setAutor(a);
        l.setCategoria(c);
        return l;
    }
}
