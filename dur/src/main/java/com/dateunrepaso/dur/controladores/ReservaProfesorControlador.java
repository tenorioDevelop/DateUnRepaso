package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservaProfesorControlador {

	@Autowired
	private AulaRepo aulaRepo;

	@Autowired
	private ReservaProfesorRepo reservaProfRepo;

	@GetMapping("/reserva-profesor")
	public String getMain(Model model) {

		List<Aula> aulas = new ArrayList<>();
		aulas = aulaRepo.findAll();

		model.addAttribute("listaAulas", aulas);

		return "ReservaProfesor";
	}

	@PostMapping("/reserva-profesor")
	public String postReservar(@RequestParam(name = "resProfAula") Long idAula,
			@RequestParam(name = "resProfFecha") LocalDate fecha, @RequestParam(name = "resProfHoraI") int horaI,
			@RequestParam(name = "resProfHoraF") int horaF, HttpSession sesion) {

		Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");

		Aula aula = aulaRepo.findById(idAula).get();

		List<ReservaProfesor> reservas = reservaProfRepo.findAll();

		for (ReservaProfesor reserva : reservas) {
			if (reserva.getAula().getId().equals(aula.getId()) && reserva.getHoraInicio().equals(horaI)
					&& reserva.getHoraFin().equals(horaF)) {
				return "redirect:/reserva-profesor";
			}
		}

		if (horaI < horaF) {
			ReservaProfesor reserva = new ReservaProfesor(null, profesor, aula, fecha, horaI, horaF);

			reservaProfRepo.save(reserva);
			return "redirect:/app";
		} else {
			return "redirect:/reserva-profesor";
		}

	}

}
