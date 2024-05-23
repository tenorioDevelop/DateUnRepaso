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
		} else {
			Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", profesor);
			model.addAttribute("tipoUsuario", "profesor");
		}

		return "Index";
	}

	public DayOfWeek fechaActual() {
		DayOfWeek dia = LocalDate.now().getDayOfWeek();

		return dia;
	}

	@GetMapping("/calendario")
	public String getCalendario() {
		return "Calendario";
	}

	@GetMapping("/clases")
	public String getClases(Model model, HttpSession sesion) {
		List<ReservaProfesor> reservaProf = new ArrayList<>();
		List<ReservaAlumno> reservaAlum = new ArrayList<>();

		if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
			Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
			List<ReservaAlumno> reservas = reservaAlumnoRepo.findAll();
			for (ReservaAlumno reservaA : reservas) {
				if (reservaA.getAlumno().getId().equals(alumno.getId())) {
					reservaAlum.add(reservaA);
				}
			}
			model.addAttribute("Reservas", reservaAlum);
		} else {
			Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
			List<ReservaProfesor> reservas = reservaProfesorRepo.findAll();
			for (ReservaProfesor reservaP : reservas) {
				if (reservaP.getProfesor().getId().equals(profesor.getId())) {
					reservaProf.add(reservaP);
				}
			}
			model.addAttribute("Reservas", reservaProf);
		}

		return "Clases";
	}

	@GetMapping("/clases/preguntarBorrar/{id}")
	public String getPreguntarBorrarReserva(@PathVariable Long id, HttpSession sesion, Model model) {
		model.addAttribute("idReserva", id);
		return "EliminarReserva";
	}

	@GetMapping("/clases/delete/{id}")
	public String getBorrarReserva(@PathVariable Long id, @RequestParam(name = "btnBorrar") String boton, HttpSession sesion) {
		if (boton.equals("SÍ")) {
			reservaProfesorRepo.deleteById(id);
		}
		
		return "redirect:/clases";
		
	}
	

}
