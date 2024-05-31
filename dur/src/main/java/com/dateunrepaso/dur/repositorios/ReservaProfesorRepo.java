package com.dateunrepaso.dur.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;

@Repository
public interface ReservaProfesorRepo extends JpaRepository<ReservaProfesor, Long> {

	Optional<ReservaProfesor> findByProfesor(Profesor profesor);

	Optional<ReservaProfesor> findByFechaReservaAndProfesorAndAula(LocalDate fechaReserva,Profesor profesor, Aula aula);

	List<ReservaProfesor> findAllByProfesor(Profesor profesor);

	List<ReservaProfesor> findByAulaId(Long id);

	List<ReservaProfesor> findAllByProfesorAndFechaReserva(Profesor profesor, LocalDate fechaReserva);

	void deleteAllByProfesor(Profesor profesor);

}
