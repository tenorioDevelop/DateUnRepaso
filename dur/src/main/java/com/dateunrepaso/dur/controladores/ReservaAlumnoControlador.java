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
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

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
	ReservaAlumnoImp reservaAlumnoImp;

	@Autowired
	ReservaProfesorImp reservaProfeImp;

	@GetMapping("/reserva-alumno")
	public String getMain(Model model) {

		// List<Profesor> profesores = profesorRepo.findAll();
		List<ReservaProfesor> reservaP = reservaProfeImp.getReservasDeProfesorActuales();

		

		model.addAttribute("listaReservasP", reservaP);

		return "ReservaAlumno";
	}

	@PostMapping("/reserva-alumno")
	public String postReservar(
			HttpSession sesion,
			RedirectAttributes atributos,
			@RequestParam(name = "idReserva") Long idReserva) {

		boolean esValido = true;

		Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");

		ReservaProfesor reservaP = reservaProfeImp.findById(idReserva).get();

		ReservaAlumno reservaA = new ReservaAlumno(null, alumno, reservaP.getProfesor(), reservaP.getAula(),
				reservaP.getFechaReserva(), reservaP.getHoraInicio(), reservaP.getHoraFin());

		// Busca si la reserva que se quiere crear ya existe
		if (reservaAlumnoImp.findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(reservaP.getAula(),
				reservaP.getProfesor(), alumno, reservaP.getFechaReserva(), reservaP.getHoraInicio()).isPresent()) {
			atributos.addFlashAttribute("Error", "Ya existe una reserva suya para ese dia a esa hora");
			esValido = false;
		}

		if (esValido) {
			reservaAlumnoImp.save(reservaA);
			return "redirect:/clases";
		} else {
			return "redirect:/reserva-alumno";
		}

	}

}
