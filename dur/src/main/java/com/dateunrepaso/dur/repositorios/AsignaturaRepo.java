package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Asignatura;

@Repository
public interface AsignaturaRepo extends JpaRepository<Asignatura, Long> {

    Optional<Asignatura> findByNombre(String nombre);

    @Modifying
    @Transactional
    @Query("UPDATE Asignatura a SET a.nombre = :nombre WHERE a.id = :id")
    int updateAsignatura(@Param("id") Long id,
            @Param("nombre") String nombre);

}
