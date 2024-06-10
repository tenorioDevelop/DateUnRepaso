package com.dateunrepaso.dur.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;

@Repository
public interface ProfesorRepo extends JpaRepository<Profesor, Long> {

	Optional<Profesor> findByCorreo(String correo);

	List<Profesor> findAllByAsignatura(Asignatura asignatura);

	@Transactional
	void deleteAllByAsignatura(Asignatura asignatura);

	@Modifying
	@Transactional
	@Query("UPDATE Profesor p SET p.nomCompleto = :nomCompleto, p.nomUsuario = :nomUsuario, p.correo = :correo, p.contrasena = :contrasena, p.fechaNac = :fechaNac, p.asignatura = :asignatura, p.dni = :dni WHERE p.id = :id")
	int updateProfesor(@Param("id") Long id,
			@Param("nomCompleto") String nomCompleto,
			@Param("nomUsuario") String nomUsuario,
			@Param("correo") String correo,
			@Param("contrasena") String contrasena,
			@Param("fechaNac") String fechaNac,
			@Param("dni") String dni,
			@Param("asignatura") Asignatura asignatura);

}
