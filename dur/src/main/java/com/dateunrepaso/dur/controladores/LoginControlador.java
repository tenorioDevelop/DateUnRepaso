package com.dateunrepaso.dur.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dateunrepaso.dur.utilidades.UtilidadesString;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginControlador {

	@Autowired
	private ProfesorRepo profesorRepo;

	@Autowired
	private AlumnoRepo alumnoRepo;

	@GetMapping("/")
	public String getLandingPage() {
		return "LandingPage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login";
	}

	@PostMapping("/login")
	public String getInicioSesion(@RequestParam(name = "correoLogin") String correoLogin,
			@RequestParam(name = "contrasenaLogin") String contrasenaLogin) {

		Optional<Alumno> alumnoOpt = alumnoRepo.findByCorreo(correoLogin);
		Optional<Profesor> profesorOpt = profesorRepo.findByCorreo(correoLogin);

		if (!alumnoOpt.isEmpty()) {
			Alumno alumno = alumnoOpt.get();
			if (alumno.getContrasena().equals(contrasenaLogin)) {
				return "redirect:/app";
			} else {
				return "redirect:/login";
			}
		} else if (!profesorOpt.isEmpty()){
			Profesor profesor = profesorOpt.get();
			if (profesor.getContrasena().equals(contrasenaLogin)) {
				return "redirect:/app";
			} else {
				return "redirect:/login";
			}
		}

		return "redirect:/login";
		
	}

	@GetMapping("/registro")
	public String getRegistro() {
		return "Registrarse";
	}

	@PostMapping("/registro")
	public String getNewUsuario(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "perfilSel") String perfil) {

		if (contrasena.equals(contrasenaRep)) {

			if (perfil.equals("esProfesor")) {

				Profesor profesor = new Profesor(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo, contrasena, fechaNac, null);

				profesorRepo.save(profesor);

			} else if (perfil.equals("esAlumno")) {

				Alumno alumno = new Alumno(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo, contrasena, fechaNac);

				alumnoRepo.save(alumno);

			}

		}

		return "redirect:/app";

	}
}
