package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;

@Service
public class ProfesorImp implements ProfesorRepo {

	@Autowired
	private ProfesorRepo profesorRepo;

	@Override
	public Optional<Profesor> findByCorreo(String correo) {
		return profesorRepo.findByCorreo(correo);
	}

	public Profesor findByCorreoAndDni(String correo, String dni) {
		List<Profesor> profesores = profesorRepo.findAll();
		Profesor profesor = new Profesor();

		for (Profesor p : profesores) {
			if (p.getCorreo().equals(correo) || p.getDni().equals(dni)) {
				profesor = p;
				return profesor;
			}
		}

		return null;
	}

	public Profesor findByDni(String dni) {
		List<Profesor> profesores = profesorRepo.findAll();
		Profesor profesor = new Profesor();

		for (Profesor p : profesores) {
			if (p.getDni().equals(dni)) {
				profesor = p;
				return profesor;
			}
		}

		return null;
	}

	public Profesor findByCorreoAndContrasena(String correo, String contrasena) {
		List<Profesor> profesores = profesorRepo.findAll();
		Profesor profesor = new Profesor();

		for (Profesor p : profesores) {
			if (p.getCorreo().equals(correo) && p.getContrasena().equals(contrasena)) {
				profesor = p;
				return profesor;
			}
		}

		return null;
	}

	@Override
	public void deleteAllByAsignatura(Asignatura asignatura) {
		profesorRepo.deleteAllByAsignatura(asignatura);
	}


	@Override
	public List<Profesor> findAllByAsignatura(Asignatura asignatura) {
		return profesorRepo.findAllByAsignatura(asignatura);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Profesor> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Profesor> entities) {
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
	public Profesor getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor getReferenceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profesor> findAll() {
		return profesorRepo.findAll();
	}

	@Override
	public List<Profesor> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> S save(S entity) {
		return profesorRepo.save(entity);
	}

	@Override
	public Optional<Profesor> findById(Long id) {
		return profesorRepo.findById(id);
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
		profesorRepo.deleteById(id);
	}

	@Override
	public void delete(Profesor entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Profesor> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Profesor> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Profesor> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Profesor> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profesor> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Profesor> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Profesor, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
