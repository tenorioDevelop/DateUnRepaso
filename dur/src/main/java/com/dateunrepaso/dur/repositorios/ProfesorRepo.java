package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;

@Repository
public interface ProfesorRepo extends JpaRepository<Profesor, Long> {

	Optional<Profesor> findByCorreo(String correo);
	
}
