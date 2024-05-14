package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;

@Repository
public interface ReservaProfesorRepo extends JpaRepository<ReservaProfesor, Long> {
	
	Optional<ReservaProfesor> findByProfesor(Profesor profesor);


}
