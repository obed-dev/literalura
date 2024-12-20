package com.example.literalura.literalura.models;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonAlias("name") private String name;
    @JsonAlias("birth_year") @Column(nullable = false) private Integer birthYear;
    @JsonAlias("death_year")private Integer deathYear;

    // Getters y setters



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
}