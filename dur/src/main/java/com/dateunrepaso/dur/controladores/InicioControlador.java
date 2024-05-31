package com.dateunrepaso.dur.controladores;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioControlador {

	@Autowired
	ReservaAlumnoRepo reservaAlumnoRepo;

	@Autowired
	ReservaProfesorRepo reservaProfesorRepo;

	@Autowired
	AlumnoRepo alumnoRepo;

	@GetMapping("/app")
	public String getApp(HttpSession sesion, Model model) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaActual = formato.format(LocalDate.now());
		String fechaPartida[] = fechaActual.split("/");
		model.addAttribute("fechaActual", fechaActual);
		// LocalDate.now().getDayOfWeek();

		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
			Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", alumno);
			model.addAttribute("tipoUsuario", "alumno");

			ReservaAlumno ultimaReserva;

			if (reservaAlumnoRepo.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now()).isEmpty()) {
				ultimaReserva = null;
			} else {
				ultimaReserva = reservaAlumnoRepo.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now())
						.get(reservaAlumnoRepo.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now()).size() - 1);
			}

			model.addAttribute("ultimaReserva", ultimaReserva);

		} else {
			Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", profesor);
			model.addAttribute("tipoUsuario", "profesor");

			ReservaProfesor ultimaReserva;

			if (reservaProfesorRepo.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).isEmpty()) {
				ultimaReserva = null;
			} else {
				ultimaReserva = reservaProfesorRepo.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).get(
						reservaProfesorRepo.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).size() - 1);
				int numeroAlumnos = reservaAlumnoRepo.findByAulaAndFechaReservaAndProfesor(ultimaReserva.getAula(),
						ultimaReserva.getFechaReserva(), profesor).get().size();
				model.addAttribute("numeroAlumnos", numeroAlumnos);
			}

			model.addAttribute("ultimaReserva", ultimaReserva);

		}

		return "Index";
	}

	public DayOfWeek fechaActual() {
		DayOfWeek dia = LocalDate.now().getDayOfWeek();

		return dia;
	}

	@GetMapping("/calendario")
	public String getCalendario(Model model, HttpSession sesion) {
		if (sesion.getAttribute("usuarioLogeado") != null) {
			model.addAttribute("usuario", sesion.getAttribute("usuarioLogeado"));
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
