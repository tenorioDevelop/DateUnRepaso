package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClasesControlador {

	@Autowired
	ReservaProfesorImp reservaProfesorImp;

	@Autowired
	ReservaAlumnoImp reservaAlumnoImp;

	@GetMapping("/clases")
	public String getClases(Model model, RedirectAttributes atributos, HttpSession sesion) {
		model.addAttribute("paginaActiva", "clases");

		if (sesion.getAttribute("usuarioLogeado") != null) {
			model.addAttribute("usuario", sesion.getAttribute("usuarioLogeado"));
			if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
				Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
				model.addAttribute("Reservas", reservaAlumnoImp.getReservasDeAlumnoPorFecha(alumno, LocalDate.now()));
				model.addAttribute("tipoUsuario", "alumno");
			} else {
				Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
				model.addAttribute("Reservas", reservaProfesorImp.getReservasDeProfesorPorFecha(profesor, LocalDate.now()));
				model.addAttribute("tipoUsuario", "profesor");
			}
	
			if (model.getAttribute("Reservas") == null) {
				atributos.addAttribute("Error", "No hay reservas disponibles para realizar");
			}
		} else {
			return "redirect:/";
		}


		return "Clases";
	}

	@GetMapping("/clases/preguntarBorrar/{id}")
	public String getPreguntarBorrarReserva(@PathVariable Long id, HttpSession sesion, Model model) {
		model.addAttribute("idReserva", id);
		return "EliminarReserva";
	}

	@Transactional
	@GetMapping("/clases/delete/{id}")
	public String getBorrarReserva(@PathVariable Long id, @RequestParam(name = "btnBorrar") String boton,
			HttpSession sesion, Model model) {

		if (boton.equals("SÍ")) {
			if (sesion.getAttribute("usuarioLogeado").getClass() == Profesor.class) {
				ReservaProfesor reservaProf = reservaProfesorImp.findById(id).get();
				reservaAlumnoImp.deleteAllByAulaAndFechaReservaAndProfesor(reservaProf.getAula(),
						reservaProf.getFechaReserva(), reservaProf.getProfesor());
				reservaProfesorImp.deleteById(id);
			}

			if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
				reservaAlumnoImp.deleteById(id);
			}
		}

		return "redirect:/clases";

	}

}
