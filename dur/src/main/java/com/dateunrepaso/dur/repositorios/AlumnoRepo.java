package com.dateunrepaso.dur.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Alumno;

@Repository
public interface AlumnoRepo extends JpaRepository<Alumno, Long> {

	Optional<Alumno> findByCorreo(String correo);

	@Modifying
	@Transactional
	@Query("UPDATE Alumno a SET a.nomCompleto = :nomCompleto, a.nomUsuario = :nomUsuario, a.correo = :correo, a.contrasena = :contrasena, a.fechaNac = :fechaNac WHERE a.id = :id")
	int updateAlumno(@Param("id") Long id,
			@Param("nomCompleto") String nomCompleto,
			@Param("nomUsuario") String nomUsuario,
			@Param("correo") String correo,
			@Param("contrasena") String contrasena,
			@Param("fechaNac") String fechaNac);

}
