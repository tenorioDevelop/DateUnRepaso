package com.dateunrepaso.dur.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClasesControlador {

    @Autowired
    ReservaProfesorRepo reservaProfesorRepo;

    @Autowired
    ReservaAlumnoRepo reservaAlumnoRepo;

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
