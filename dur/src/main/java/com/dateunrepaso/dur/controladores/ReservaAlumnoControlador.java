package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservaAlumnoControlador {

	@Autowired
	AlumnoRepo alumnoRepo;

	@Autowired
	ProfesorRepo profesorRepo;

	@Autowired
	ReservaAlumnoRepo reservaAlumnoRepo;
	
	@Autowired
	ReservaAlumnoImp reservaAlumnoImp;

	@GetMapping("/reserva-alumno")
	public String getMain(Model model) {

		List<Profesor> profesores = profesorRepo.findAll();

		model.addAttribute("listaProfesores", profesores);

		return "ReservaAlumno";
	}

	@PostMapping("/reserva-alumno")
	public String postReservar(
			HttpSession sesion,
			@RequestParam(name = "profesor") Long idProfesor,
			@RequestParam(name = "asignatura") Long asignatura,
			@RequestParam(name = "fechaReserva") LocalDate fechaReserva,
			@RequestParam(name = "horaI") int horaInicio,
			@RequestParam(name = "horaFin") int horaFin
			) {
		
		Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");

		ReservaAlumno reserva = new ReservaAlumno(null, 
				alumno, 
				profesorRepo.findById(idProfesor).get(),
				reservaAlumnoImp.getAulasByProfesorAndFechaReserva(idProfesor, fechaReserva),
				fechaReserva,
				horaInicio,
				horaFin
				)	;
		
		return "redirect:/app";
	}

}
