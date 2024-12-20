package com.example.literalura.literalura.repositories;

import com.example.literalura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    // Derived query para encontrar autores únicos
    @Query("SELECT a FROM Autor a WHERE a.name = :name AND " +
            "(a.birthYear = :birthYear OR :birthYear IS NULL) AND " +
            "(a.deathYear = :deathYear OR :deathYear IS NULL)")
    Optional<Autor> findAutor(@Param("name") String name,
                              @Param("birthYear") Integer birthYear,
                              @Param("deathYear") Integer deathYear);
    Optional<Autor> findByNameAndBirthYearAndDeathYear(String name, Integer birthYear, Integer deathYear);
    List<Autor> findByBirthYearLessThanEqualAndDeathYearGreaterThan(int birthYear, int deathYear);
    Optional<Autor> findByName(String name);
}
