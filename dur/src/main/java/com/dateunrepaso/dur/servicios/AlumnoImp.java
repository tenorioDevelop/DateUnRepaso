package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;

@Service
public class AlumnoImp implements AlumnoRepo {

	@Autowired
	private AlumnoRepo alumnoRepo;

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Alumno> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Alumno> entities) {
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
	public Alumno getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno getReferenceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alumno> findAll() {
		return alumnoRepo.findAll();
	}

	@Override
	public List<Alumno> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> S save(S entity) {
		return alumnoRepo.save(entity);
	}

	@Override
	public Optional<Alumno> findById(Long id) {
		return alumnoRepo.findById(id);
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
		alumnoRepo.deleteById(id);
	}

	@Override
	public void delete(Alumno entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Alumno> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Alumno> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Alumno> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Alumno> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Alumno> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Alumno> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Alumno, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Alumno> findByCorreo(String correo) {
		return alumnoRepo.findByCorreo(correo);
	}

	public Alumno findByCorreoAndDni(String correo, String dni) {
		List<Alumno> alumnos = alumnoRepo.findAll();
		Alumno alumno = new Alumno();

		for (Alumno a : alumnos) {
			if (a.getCorreo().equals(correo) || a.getDni().equals(dni)) {
				alumno = a;
				return alumno;
			}
		}

		return null;
	}

	public Alumno findByDni(String dni) {
		List<Alumno> alumnos = alumnoRepo.findAll();
		Alumno alumno = new Alumno();

		for (Alumno a : alumnos) {
			if (a.getDni().equals(dni)) {
				alumno = a;
				return alumno;
			}
		}

		return null;
	}

	public Alumno findByCorreoAndContrasena(String correo, String contrasena) {
		List<Alumno> alumnos = alumnoRepo.findAll();
		Alumno alumno = new Alumno();

		for (Alumno a : alumnos) {
			if (a.getContrasena().equals(contrasena) && a.getCorreo().equals(correo)) {
				alumno = a;
				return alumno;
			}
		}

		return null;

	}

}
