package com.example.literalura.literalura.service;



import com.example.literalura.literalura.models.Autor;
import com.example.literalura.literalura.models.Libro;
import com.example.literalura.literalura.repositories.AutorRepository;
import com.example.literalura.literalura.repositories.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void manejarOpcion(int opcion, Scanner scanner) {
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo(scanner);
            case 2 -> listarTodosLosLibros();
            case 3 -> listarTodosLosAutores();
            case 4 -> listarAutoresVivosEnAnio(scanner);
            case 5 -> mostrarCantidadLibrosPorIdioma(scanner);
            default -> System.out.println("Opción no válida.");
        }
    }

    private void buscarLibroPorTitulo(Scanner scanner) {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();

        try {
            String encodedTitle = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books/?search=" + encodedTitle))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode book = root.path("results").get(0);

            if (book != null) {
                String bookTitle = book.path("title").asText();
                JsonNode authorNode = book.path("authors").get(0);
                String authorName = authorNode.path("name").asText();

                // Validar si el libro ya existe
                if (libroRepository.findByTitleAndAuthorName(bookTitle, authorName).isPresent()) {
                    System.out.println("El libro ya está registrado en la base de datos.");
                    return;
                }

                Integer birthYear = authorNode.path("birth_year").isNumber() ? authorNode.path("birth_year").asInt() : null;
                Integer deathYear = authorNode.path("death_year").isNumber() ? authorNode.path("death_year").asInt() : null;
                String language = book.path("languages").get(0).asText();
                int downloadCount = book.path("download_count").asInt();

                Autor autor = autorRepository.findByNameAndBirthYearAndDeathYear(authorName, birthYear, deathYear)
                        .orElseGet(() -> {
                            Autor nuevoAutor = new Autor();
                            nuevoAutor.setName(authorName);
                            nuevoAutor.setBirthYear(birthYear);
                            nuevoAutor.setDeathYear(deathYear);
                            return autorRepository.save(nuevoAutor);
                        });

                Libro libro = new Libro();
                libro.setTitle(bookTitle);
                libro.setLanguage(language);
                libro.setDownloadCount(downloadCount);
                libro.setAuthor(autor);
                libroRepository.save(libro);

                System.out.printf("Libro guardado con éxito: \nTítulo: %s\nAutor: %s\nAño de Nacimiento: %s\nAño de Fallecimiento: %s\nIdioma: %s\nDescargas: %d\n\n",
                        libro.getTitle(), autor.getName(),
                        autor.getBirthYear() != null ? autor.getBirthYear() : "N/A",
                        autor.getDeathYear() != null ? autor.getDeathYear() : "N/A",
                        libro.getLanguage(), libro.getDownloadCount());
            } else {
                System.out.println("No se encontraron resultados para el título proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }
    }



    private void listarTodosLosLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
        } else {
            System.out.println("\nLista de libros guardados:\n");
            for (Libro libro : libros) {
                System.out.printf("Título: %s | Autor: %s | Idioma: %s | Descargas: %d\n",
                        libro.getTitle(), libro.getAuthor().getName(), libro.getLanguage(), libro.getDownloadCount());
            }
            System.out.println();
        }
    }

    private void listarTodosLosAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
        } else {
            System.out.println("\nLista de autores guardados:\n");
            for (Autor autor : autores) {
                System.out.printf("Nombre: %s | Año de Nacimiento: %d | Año de Fallecimiento: %d\n",
                        autor.getName(), autor.getBirthYear(), autor.getDeathYear());
            }
            System.out.println();
        }
    }

    private void listarAutoresVivosEnAnio(Scanner scanner) {
        System.out.print("Ingrese el año: ");
        int anio = scanner.nextInt();
        List<Autor> autores = autorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThan(anio, anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("\nAutores vivos en el año " + anio + ":\n");
            for (Autor autor : autores) {
                System.out.printf("Nombre: %s | Año de Nacimiento: %d\n", autor.getName(), autor.getBirthYear());
            }
        }
    }

    private void mostrarCantidadLibrosPorIdioma(Scanner scanner) {
        System.out.print("Ingrese el idioma a consultar (ej: en, es, fr): ");
        String idioma = scanner.nextLine();

        List<Libro> libros = libroRepository.findAll();

        Map<String, Long> conteoPorIdioma = libros.stream()
                .filter(libro -> libro.getLanguage().equalsIgnoreCase(idioma))
                .collect(Collectors.groupingBy(Libro::getLanguage, Collectors.counting()));

        if (conteoPorIdioma.isEmpty()) {
            System.out.printf("No se encontraron libros en el idioma '%s'.\n", idioma);
        } else {
            System.out.println("\nCantidad de libros por idioma:\n");
            conteoPorIdioma.forEach((lang, count) -> System.out.printf("Idioma: %s | Cantidad de libros: %d\n", lang, count));
        }
    }
}

