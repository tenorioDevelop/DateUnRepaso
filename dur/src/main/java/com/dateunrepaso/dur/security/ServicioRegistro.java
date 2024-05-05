package com.dateunrepaso.dur.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;

import com.dateunrepaso.dur.utilidades.UtilidadesString;

@Service
public class ServicioRegistro {

	@Autowired
	private AlumnoRepo alumnoRepo;

	@Autowired
	private ProfesorRepo profesorRepo;

	@Autowired
	private PasswordEncoder pwdEncoder;

	public boolean registrarSesion(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "perfilSel") String perfil) {
		
		if (contrasena.equals(contrasenaRep)) {

			if (perfil.equals("esProfesor")) {

				Profesor profesor = new Profesor(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo, pwdEncoder.encode(contrasena), fechaNac, null);

				profesorRepo.save(profesor);

			} else if (perfil.equals("esAlumno")) {

				Alumno alumno = new Alumno(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo, pwdEncoder.encode(contrasena), fechaNac);

				alumnoRepo.save(alumno);

			}

			return true;

		} else {
			return false;
		}

	}

	public boolean iniciarSesion(@RequestParam(name = "correoLogin") String correoLogin,
			@RequestParam(name = "contrasenaLogin") String contrasenaLogin) {
		Optional<Alumno> alumnoOpt = alumnoRepo.findByCorreo(correoLogin);
		Optional<Profesor> profesorOpt = profesorRepo.findByCorreo(correoLogin);

		if (!alumnoOpt.isEmpty()) {
			Alumno alumno = alumnoOpt.get();
			if (alumno.getContrasena().equals(contrasenaLogin)) {
				return true;
			} else {
				return false;
			}
		} else if (!profesorOpt.isEmpty()) {
			Profesor profesor = profesorOpt.get();
			if (profesor.getContrasena().equals(contrasenaLogin)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}
