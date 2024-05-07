package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;

@Repository
public interface AlumnoRepo extends JpaRepository<Alumno, Long>{
	
//	@Query("SELECT a FROM Alumno a WHERE a.correo = ?1")
//	Alumno findByCorreo(String correo);
	
//	https://www.youtube.com/watch?v=tHsPyC23oww
	
	Optional<Alumno> findByCorreo(String correo);
	
}
