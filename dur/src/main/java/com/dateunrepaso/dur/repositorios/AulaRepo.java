package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Aula;

@Repository
public interface AulaRepo extends JpaRepository<Aula, Long> {

    Optional<Aula> findByNombre(String nombre);

}
