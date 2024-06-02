package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;

@Service
public class AlumnoImp {

	@Autowired
	private AlumnoRepo alumnoRepo;

	public List<Alumno> findAll() {
		return alumnoRepo.findAll();
	}

	public <S extends Alumno> S save(S entity) {
		return alumnoRepo.save(entity);
	}

	public Optional<Alumno> findById(Long id) {
		return alumnoRepo.findById(id);
	}
	
	public void deleteById(Long id) {
		alumnoRepo.deleteById(id);
	}

	public Optional<Alumno> findByCorreo(String correo) {
		return alumnoRepo.findByCorreo(correo);
	}

	@Transactional
    public void actualizarAlumno(Long id, String nomCompleto, String nomUsuario, String correo, String contrasena, String fechaNac) {
        alumnoRepo.updateAlumno(id, nomCompleto, nomUsuario, correo, contrasena, fechaNac);
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
