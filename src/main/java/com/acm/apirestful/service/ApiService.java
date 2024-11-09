package com.acm.apirestful.service;

import com.acm.apirestful.presentation.dto.AutorDTO;
import com.acm.apirestful.presentation.dto.CategoriaDTO;
import com.acm.apirestful.presentation.dto.LibraryDTO;
import com.acm.apirestful.presentation.dto.LibroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Slf4j
@Service
public class ApiService {

    @Value("${myapp.libraryapi.base}")
    private String libraryApiBaseUrl;

    private RestTemplate restTemplate;
    public ApiService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LibraryDTO searchLibraryByAutor(String autorNombre) {
        try {

            JsonNode searchLibros = searchQuery("author",autorNombre);
            LibraryDTO l = searchlibros(searchLibros);
            return l;

        } catch (NullPointerException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

    public LibraryDTO searchLibraryByTitulo(String titulo) {
        try {

            JsonNode searchLibros = searchQuery("title", titulo);
            LibraryDTO l = searchlibros(searchLibros);
            return l;

        } catch (NullPointerException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

    public LibraryDTO searchLibraryByCategoria(String categoriaNombre) {
        try {

            JsonNode searchLibros = searchQuery("subject",categoriaNombre);
            LibraryDTO l = searchlibros(searchLibros);
            return l;

        } catch (NullPointerException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

    private JsonNode searchQuery(String criterio, String nombreCriterio){
        StringBuilder searchStr = new StringBuilder();
        searchStr.append(libraryApiBaseUrl);
        log.info(libraryApiBaseUrl);
        searchStr.append("/search.json?").append(criterio).append("=").append(nombreCriterio);
        return restTemplate.getForObject(searchStr.toString(), JsonNode.class);
    }

    private LibraryDTO searchlibros(JsonNode searchLibros){
        try {
            int searchLibroCantidad = Integer.parseInt(searchLibros.get("numFound").asText());
            if (searchLibroCantidad == 0){
                return null;
            }
            JsonNode searchLibroRequest = searchLibros.get("docs");

            JsonNode libroKeyJson = searchLibroRequest.get(0);
            log.info(libroKeyJson.toString());

            // Extrae las key y nombre del autor para reutilizarlo en la busqueda precisa
            String lLibro = libroKeyJson.get("key").asText();
            String lAutor = libroKeyJson.get("author_key").get(0).asText();
            String lCategoria = libroKeyJson.get("subject_key").get(0).asText();
            log.info(lLibro);
            log.info(lAutor);
            log.info(lCategoria);

            // Obtiene los datos del libro llamando a la función
            LibroDTO libro = searchLibroByKey(lLibro);
            // Obtiene los datos del autor llamando a la función
            AutorDTO autor = searchAutorByKey(lAutor);
            // Obtiene los datos de la categoria llamando a la función
            CategoriaDTO categoria = searchCategoriaByKey(lCategoria);

            if ((libro == null) || (autor == null) || (categoria == null)){
                return null;
            }
            // Devuelve el DTO del libro encontrado a la lista de libros
            LibraryDTO l = new LibraryDTO(libro, categoria, autor);

            return l;
        } catch (NullPointerException e){
            log.error("Error searching: ", e);
        }
        return null;
    }

    private LibroDTO searchLibroByKey(String key) {
        try{

            StringBuilder libroStr = new StringBuilder();
            libroStr.append(libraryApiBaseUrl);
            libroStr.append(key);
            log.info(libroStr.toString());
            return restTemplate.getForObject(libroStr.toString(), LibroDTO.class);

        } catch (NullPointerException | RestClientException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

    private AutorDTO searchAutorByKey(String key) {
        try{

            StringBuilder autorStr = new StringBuilder();
            autorStr.append(libraryApiBaseUrl);
            autorStr.append("/author/").append(key);
            log.info(autorStr.toString());
            return restTemplate.getForObject(autorStr.toString(), AutorDTO.class);

        } catch (NullPointerException | RestClientException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

    private CategoriaDTO searchCategoriaByKey(String key) {
        try{

            StringBuilder categoriaStr = new StringBuilder();
            categoriaStr.append(libraryApiBaseUrl);
            categoriaStr.append("/subjects/").append(key);
            log.info(categoriaStr.toString());
            return restTemplate.getForObject(categoriaStr.toString(), CategoriaDTO.class);

        } catch (NullPointerException | RestClientException e) {
            log.error("Error searching: ", e);
        }
        return null;
    }

}
