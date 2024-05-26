package com.dateunrepaso.dur.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;

@Repository
public interface ReservaAlumnoRepo extends JpaRepository<ReservaAlumno, Long> {

	Optional<ReservaAlumno> findByAlumno(Alumno alumno);

	List<ReservaAlumno> findByFechaReservaAndAula(LocalDate fecha, Aula aula);

	List<ReservaAlumno> findAllByAlumno(Alumno alumno);

	Optional<ReservaAlumno> findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(
			Aula aula, Profesor profesor, Alumno alumno, LocalDate fechaReserva, Integer horaInicio);

}
