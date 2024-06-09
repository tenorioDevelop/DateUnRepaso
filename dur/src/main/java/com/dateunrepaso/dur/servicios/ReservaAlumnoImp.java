package com.dateunrepaso.dur.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;

@Service
public class ReservaAlumnoImp {

	@Autowired
	private ReservaAlumnoRepo reservaAlumRepo;

	/**
	 * @param alumno
	 * @param fecha
	 * @return Las reservas segun el alumno indicado de la fecha indicada y adelante
	 */
	public List<ReservaAlumno> getReservasDeAlumnoPorFecha(Alumno alumno, LocalDate fecha) {
		return this.findAllByAlumno(alumno)
				.stream()
				.filter(a -> a.getFechaReserva().isEqual(fecha) || a.getFechaReserva().isAfter(fecha))
				.collect(Collectors.toList());
	}

	public List<ReservaAlumno> getReservasAlumno(Alumno alumno) {
		return this.findAllByAlumno(alumno);
	}

	public boolean reservaSuperaNumMaxAlumnos(Aula aula, LocalDate fecha) {
		return this.findByFechaReservaAndAula(fecha, aula).size() > aula.getCantidadMaxAlumnos();
	}

	public Optional<ReservaAlumno> findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(Aula aula,
			Profesor profesor, Alumno alumno, LocalDate fechaReserva, Integer horaInicio) {
		return reservaAlumRepo.findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(aula, profesor, alumno,
				fechaReserva, horaInicio);
	}

	public void deleteAllByAulaAndFechaReservaAndProfesor(Aula aula, LocalDate fechaReserva, Profesor profesor) {
		reservaAlumRepo.deleteAllByAulaAndFechaReservaAndProfesor(aula, fechaReserva, profesor);
	}

	public Optional<List<ReservaAlumno>> findByAulaAndFechaReservaAndProfesor(Aula aula, LocalDate fechaReserva,
			Profesor profesor) {
		return reservaAlumRepo.findByAulaAndFechaReservaAndProfesor(aula, fechaReserva, profesor);
	}

	public List<ReservaAlumno> findAllByProfesorId(Long id) {
		return reservaAlumRepo.findAllByProfesorId(id);
	}

	public List<ReservaAlumno> findAll() {
		return reservaAlumRepo.findAll();
	}

	public <S extends ReservaAlumno> S save(S entity) {
		return reservaAlumRepo.save(entity);
	}

	public Optional<ReservaAlumno> findById(Long id) {
		return Optional.empty();
	}

	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}
  
	public void deleteById(Long id) {
		reservaAlumRepo.deleteById(id);
	}

	public void deleteAll(Iterable<? extends ReservaAlumno> entities) {
		reservaAlumRepo.deleteAll(entities);
	}

	public List<ReservaAlumno> findByFechaReservaAndAula(LocalDate fecha, Aula aula) {
		return reservaAlumRepo.findByFechaReservaAndAula(fecha, aula);
	}

	public List<ReservaAlumno> findAllByAlumno(Alumno alumno) {
		return reservaAlumRepo.findAllByAlumno(alumno);
	}

	public List<ReservaAlumno> findAllByAlumnoAndFechaReserva(Alumno alumno, LocalDate fechaReserva) {
		return reservaAlumRepo.findAllByAlumnoAndFechaReserva(alumno, fechaReserva);
	}
}
