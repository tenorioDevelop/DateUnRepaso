package com.dateunrepaso.dur.controladores;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;

@Controller
public class LoginControlador {
	
	@Autowired
	private ProfesorRepo repoProfesor;
	
	@Autowired
	private AlumnoRepo repoAlumno;

	@GetMapping("/")
	public String getLandingPage() {
		return "LandingPage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login";
	}

	@GetMapping("/registro")
	public String getRegistro() {
		return "Registrarse";
	}

	@PostMapping("/registrarse")
	public void getNewUsuario(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep, @RequestParam(name = "perfilSel") String perfil) {
		if (contrasena.equals(contrasenaRep)) {
			if (perfil.equals("esProfesor")) {
				
				Profesor profesor = new Profesor(null, dni, nombre, null, correo, contrasena, fechaNac, null);
				
				repoProfesor.save(profesor);
				
			} else if (perfil.equals("esAlumno")) {
				Alumno alumno = new Alumno(null, dni, nombre, null, correo, contrasena, fechaNac);
				
				repoAlumno.save(alumno);
			}
		}
	}
}
