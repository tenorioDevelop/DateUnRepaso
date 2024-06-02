package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Aula;

@Repository
public interface AulaRepo extends JpaRepository<Aula, Long> {

    Optional<Aula> findByNombre(String nombre);

    @Modifying
    @Transactional
    @Query("UPDATE Aula a SET a.cantidadMaxAlumnos = :cantidadMaxAlumnos, a.nombre = :nombre WHERE a.id = :id")
    int updateAula(@Param("id") Long id,
            @Param("cantidadMaxAlumnos") Integer cantidadMaxAlumnos,
            @Param("nombre") String nombre);

}
