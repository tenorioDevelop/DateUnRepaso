package com.dateunrepaso.dur.controladores;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;
import com.dateunrepaso.dur.utilidades.UtilidadesControladores;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioControlador {

	@Autowired
	ReservaAlumnoRepo reservaAlumnoRepo;

	@Autowired
	ReservaProfesorRepo reservaProfesorRepo;

	@Autowired
	AlumnoRepo alumnoRepo;

	@GetMapping("/app")
	@ResponseBody
	public String getApp(Model model) {
		return "hola";
	}

	public DayOfWeek fechaActual() {
		DayOfWeek dia = LocalDate.now().getDayOfWeek();

		return dia;
	}

	@GetMapping("/calendario")
	public String getCalendario(Model model, HttpSession sesion) {
		if (sesion.getAttribute("usuarioLogeado") != null) {
			model.addAttribute("usuario", sesion.getAttribute("usuarioLogeado"));
			model.addAttribute("paginaActiva", "calendario");
			return "Calendario";
		}
		return "redirect:/";
	}

	@GetMapping("/cerrarSesion")
	public String getCerrarSesion(HttpSession sesion) {
		sesion.removeAttribute("usuarioLogeado");
		return "redirect:/";
	}

}
