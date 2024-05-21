package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.AulaRepo;
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
			RedirectAttributes atributos,
			@RequestParam(name = "profesor") Long idProfesor,
			@RequestParam(name = "asignatura", required = false) Long asignatura,
			@RequestParam(name = "fechaReserva") LocalDate fechaReserva,
			@RequestParam(name = "horaInc") int horaInicio,
			@RequestParam(name = "horaFin") int horaFin) {

		Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
		Profesor profesor = profesorRepo.findById(idProfesor).get();
		//Recoge el aula segun el profesor y la fecha indicada
		Aula aula = reservaAlumnoImp.getAulasByProfesorAndFechaReserva(idProfesor, fechaReserva);

		boolean esValido = true;

		//validaciones


		if(aula == null){
			atributos.addFlashAttribute("Error", "No hay una reserva del profesor para ese dia");
			esValido = false;
		} else if(reservaAlumnoImp.reservaSuperaNumMaxAlumnos(aula, fechaReserva)){ //TODO hacer que funcione
			atributos.addFlashAttribute("Error", "Se ha superado el numero maximo de reservas para ese dia");
			esValido = false;
		}

		ReservaAlumno reserva = new ReservaAlumno(null,
				alumno,
				profesor,
				aula,
				fechaReserva,
				horaInicio,
				horaFin);

		
		if(esValido){
			reservaAlumnoRepo.save(reserva);
			return "redirect:/app";
		} else {
			return "redirect:/reserva-alumno";
		}

	}

}
