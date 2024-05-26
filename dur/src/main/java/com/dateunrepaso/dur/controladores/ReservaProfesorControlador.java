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
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaProfesorControlador {

	@Autowired
	private AulaRepo aulaRepo;

	@Autowired
	private ReservaProfesorImp reservaProfImp;

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
			@RequestParam(name = "resProfHoraF") int horaF, HttpSession sesion, RedirectAttributes atributos) {

		Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");

		Aula aula = aulaRepo.findById(idAula).get();

		List<ReservaProfesor> reservas = reservaProfImp.findAll();

		// Comprobaciones de conflictos de las fechas
		if (reservaProfImp.findByFechaReservaAndProfesorAndAula(fecha, profesor, aulaRepo.findById(idAula).get())
				.isPresent()) {
			// Comprobar que la reserva no genera conflicto con otra existente
			if (horaI >= reservaProfImp
					.findByFechaReservaAndProfesorAndAula(fecha, profesor, aulaRepo.findById(idAula).get()).get()
					.getHoraInicio() &&
					horaI <= reservaProfImp
							.findByFechaReservaAndProfesorAndAula(fecha, profesor, aulaRepo.findById(idAula).get())
							.get().getHoraFin()) {
				atributos.addFlashAttribute("Error", "Ya existe una reserva en ese tramo de horario");
				return "redirect:/reserva-profesor";
			}

		}

		for (ReservaProfesor reserva : reservas) {
			System.out.println("\n" + fecha + "\n");
			if (reserva.getAula().getId().equals(aula.getId()) && reserva.getHoraInicio().equals(horaI)
					&& reserva.getHoraFin().equals(horaF) && reserva.getFechaReserva().equals(fecha)) {
				atributos.addFlashAttribute("Error", "Ya existe una reserva en ese horario");
				return "redirect:/reserva-profesor";
			} else if (reserva.getProfesor().getId().equals(profesor.getId()) && reserva.getHoraInicio().equals(horaI)
					&& reserva.getHoraFin().equals(horaF) && reserva.getFechaReserva().equals(fecha)) {
				atributos.addFlashAttribute("Error", "Ya tienes una reserva en ese horario");
				return "redirect:/reserva-profesor";
			} else if (reserva.getAula().getId() != aula.getId() && reserva.getFechaReserva().equals(fecha)
					&& (horaI >= reserva.getHoraInicio() && horaF <= reserva.getHoraFin()
							|| (horaI >= reserva.getHoraInicio() && horaF >= reserva.getHoraFin()))) {
				atributos.addFlashAttribute("Error", "Ya existe una reserva en ese horario");
				return "redirect:/reserva-profesor";
			}

		}

		if (horaI < horaF) {
			ReservaProfesor reserva = new ReservaProfesor(null, profesor, aula, fecha, horaI, horaF);
			reservaProfImp.save(reserva);
			return "redirect:/app";
		} else {
			atributos.addFlashAttribute("Error", "La hora final no puede ser antes que la hora inicio");
			return "redirect:/reserva-profesor";
		}

	}

}
