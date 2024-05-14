package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.ReservaAlumno;

@Repository
public interface ReservaAlumnoRepo extends JpaRepository<ReservaAlumno, Long> {
	
	Optional<ReservaAlumno> findByAlumno(Alumno alumno);

}
