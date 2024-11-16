package com.acm.apirestful.services;

import com.acm.apirestful.persistence.entity.Autor;
import com.acm.apirestful.persistence.entity.Categoria;
import com.acm.apirestful.persistence.entity.Libro;
import com.acm.apirestful.persistence.repository.LibroRepository;
import com.acm.apirestful.persistence.repository.PrestamoRepository;
import com.acm.apirestful.presentation.dto.AutorDTO;
import com.acm.apirestful.presentation.dto.CategoriaDTO;
import com.acm.apirestful.presentation.dto.LibraryDTO;
import com.acm.apirestful.presentation.dto.LibroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final ApiService apiService;
    private final PrestamoRepository prestamoRepository;

    public LibroService(LibroRepository libroRepository, ApiService apiService, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.apiService = apiService;
        this.prestamoRepository = prestamoRepository;
    }

    public void eliminarLibroByTitulo(String titulo) {
        libroRepository.deleteLibroByTitulo(titulo);
    }

    public void actualizarLibroByTitulo(Libro libro, String titulo) {
        libroRepository.updateLibroByTitulo(libro, titulo);
    }

    public void guardarLibro(Libro libro) {
        libroRepository.save(libro);
    }

    public List<LibraryDTO> searchLibrosByPrestamo(Short idPrestamo, Short idCliente) {
        List<Libro> libroSearch = libroRepository.findByPrestamoAndCliente(idPrestamo, idCliente);
        List<LibraryDTO> libros = new ArrayList<>();
        for (Libro l: libroSearch) {
            LibraryDTO libro = mapToDTO(l);
            libros.add(libro);
        }
        return libros;
    }

    public List<LibraryDTO> searchLibrosByPrestamo(Short idPrestamo, Short idCliente, String username) {
        boolean perteneceCliente = prestamoRepository.existsByIdAndClienteUserUsername(idPrestamo, username);

        if (perteneceCliente) {
            List<Libro> libroSearch = libroRepository.findByPrestamoAndCliente(idPrestamo, idCliente);
            List<LibraryDTO> libros = new ArrayList<>();
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            return libros;
        }
        return null;
    }

    public List<LibraryDTO> searchLibrosByCategoria(String nombreCategoria) {
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
                return libros;
            }
            log.info("No se encontraron libros.");
            return null;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            return libros;
        }
    }

    public List<LibraryDTO> searchLibrosByTitulo(String titulo) {
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
                return libros;
            }
            log.info("No se encontraron libros.");
            return null;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            return libros;
        }
    }

    public List<LibraryDTO> searchLibrosByAutor(String nombreAutor) {
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
                return libros;
            }
            log.info("No se encontraron libros.");
            return null;
        } else {
            for (Libro l: libroSearch) {
                LibraryDTO libro = mapToDTO(l);
                libros.add(libro);
            }
            log.info("Se encontraron libros ya guardados.");
            return libros;
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
