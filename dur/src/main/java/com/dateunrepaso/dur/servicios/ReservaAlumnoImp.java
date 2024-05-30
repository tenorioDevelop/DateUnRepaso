package com.dateunrepaso.dur.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;

@Service
public class ReservaAlumnoImp implements ReservaAlumnoRepo {

	@Autowired
	private ReservaAlumnoRepo reservaAlumRepo;

	/**
	 * @param alumno
	 * @param fecha
	 * @return Las reservas segun el alumno indicado de la fecha indicada y adelante
	 */
	public List<ReservaAlumno> getReservasDeAlumnoPorFecha(Alumno alumno, LocalDate fecha){
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

	@Override
	public Optional<ReservaAlumno> findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(Aula aula,
			Profesor profesor, Alumno alumno, LocalDate fechaReserva, Integer horaInicio) {
		return reservaAlumRepo.findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(aula, profesor, alumno,
				fechaReserva, horaInicio);
	}

	@Override
	public void deleteAllByAulaAndFechaReservaAndProfesor(Aula aula, LocalDate fechaReserva, Profesor profesor) {
		reservaAlumRepo.deleteAllByAulaAndFechaReservaAndProfesor(aula,fechaReserva,profesor);
	}

	@Override
	public Optional<List<ReservaAlumno>> findByAulaAndFechaReservaAndProfesor(Aula aula, LocalDate fechaReserva,
			Profesor profesor) {
		return reservaAlumRepo.findByAulaAndFechaReservaAndProfesor(aula, fechaReserva, profesor);
	}

	@Override
	public List<ReservaAlumno> findAllByProfesorId(Long id) {
		return reservaAlumRepo.findAllByProfesorId(id);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends ReservaAlumno> S saveAndFlush(S entity) {

		return null;
	}

	@Override
	public <S extends ReservaAlumno> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<ReservaAlumno> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public ReservaAlumno getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaAlumno getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaAlumno getReferenceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservaAlumno> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservaAlumno> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> S save(S entity) {
		return reservaAlumRepo.save(entity);
	}

	@Override
	public Optional<ReservaAlumno> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		reservaAlumRepo.deleteById(id);
	}

	@Override
	public void delete(ReservaAlumno entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends ReservaAlumno> entities) {
		reservaAlumRepo.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ReservaAlumno> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ReservaAlumno> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends ReservaAlumno> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ReservaAlumno> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends ReservaAlumno> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends ReservaAlumno, R> R findBy(Example<S> example,
			Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ReservaAlumno> findByAlumno(Alumno alumno) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ReservaAlumno> findByFechaReservaAndAula(LocalDate fecha, Aula aula) {
		return reservaAlumRepo.findByFechaReservaAndAula(fecha, aula);
	}

	@Override
	public List<ReservaAlumno> findAllByAlumno(Alumno alumno) {
		return reservaAlumRepo.findAllByAlumno(alumno);
	}

	

	


}
