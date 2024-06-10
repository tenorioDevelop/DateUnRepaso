package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;

@Service
public class ProfesorImp {

	@Autowired
	private ProfesorRepo profesorRepo;

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

	@Transactional
	public void actualizarProfesor(Long id, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac, String dni, Asignatura asignatura) {
		profesorRepo.updateProfesor(id, nomCompleto, nomUsuario, correo, contrasena, fechaNac, dni, asignatura);
	}

	@Transactional
	public void deleteAllByAsignatura(Asignatura asignatura) {
		profesorRepo.deleteAllByAsignatura(asignatura);
	}

	public List<Profesor> findAllByAsignatura(Asignatura asignatura) {
		return profesorRepo.findAllByAsignatura(asignatura);
	}

	public List<Profesor> findAll() {
		return profesorRepo.findAll();
	}

	public <S extends Profesor> S save(S entity) {
		return profesorRepo.save(entity);
	}

	public Optional<Profesor> findById(Long id) {
		return profesorRepo.findById(id);
	}

	public void deleteById(Long id) {
		profesorRepo.deleteById(id);
	}

}
