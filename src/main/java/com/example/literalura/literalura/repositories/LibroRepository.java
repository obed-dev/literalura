package com.example.literalura.literalura.repositories;

import com.example.literalura.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Verifica si un libro con el título dado ya existe
    Optional<Libro> findByTitleAndAuthorName(String title, String authorName);
    List<Libro> findByLanguage(String language);
    long countByLanguage(String language);
}

