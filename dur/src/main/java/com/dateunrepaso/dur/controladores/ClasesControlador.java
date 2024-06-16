package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.EmailServiceImp;
import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;

@Controller
@PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
@RequestMapping("/clases")
public class ClasesControlador {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	AlumnoImp alumnoImp;

	@Autowired
	ProfesorImp profesorImp;

	@Autowired
	ReservaProfesorImp reservaProfesorImp;

	@Autowired
	ReservaAlumnoImp reservaAlumnoImp;

	@Autowired
	EmailServiceImp emailServiceImp;

	@Transactional
	@GetMapping("")
	public String getClases(Model model, RedirectAttributes atributos) {
		model.addAttribute("paginaActiva", "clases");
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		if (usuario.getRol() == Roles.ALUMNO) {
			Alumno alumno = alumnoImp.findById(usuario.getId()).get();
			model.addAttribute("Reservas", reservaAlumnoImp.getReservasDeAlumnoPorFecha(alumno, LocalDate.now()));
			model.addAttribute("tipoUsuario", "alumno");
		} else {
			Profesor profesor = profesorImp.findById(usuario.getId()).get();
			model.addAttribute("Reservas", reservaProfesorImp.getReservasDeProfesorPorFecha(profesor, LocalDate.now()));
			model.addAttribute("tipoUsuario", "profesor");
		}

		if (model.getAttribute("Reservas") == null) {
			atributos.addAttribute("Error", "No hay reservas disponibles para realizar");
		}

		

		return "Clases";

	}

	@GetMapping("/preguntarBorrar/{id}")
	public String getPreguntarBorrarReserva(@PathVariable Long id, Model model) {
		model.addAttribute("idReserva", id);
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);
		return "EliminarReserva";
	}

	@Transactional
	@GetMapping("/delete/{id}")
	public String getBorrarReserva(@PathVariable Long id, @RequestParam(name = "btnBorrar") String boton, Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		if (boton.equals("SÍ")) {
			if (usuario.getRol() == Roles.ALUMNO) {
				reservaAlumnoImp.deleteById(id);
			} else {
				ReservaProfesor reservaProf = reservaProfesorImp.findById(id).get();
				reservaProf.getLstReservaAlumno().forEach(reserva -> {
					EmailDTO emailDTO = new EmailDTO(reserva.getAlumno().getCorreo(), "Clase cancelada",
							"Se ha cancelado su clase con el profesor: " + reserva.getProfesor().getNomCompleto()
									+ ", del dia: " + reserva.getFechaReserva() + " en el aula: "
									+ reserva.getAula().getNombre());
					emailServiceImp.enviarCorreo(emailDTO);
				});
				reservaAlumnoImp.deleteAllByAulaAndFechaReservaAndProfesor(reservaProf.getAula(),
						reservaProf.getFechaReserva(), reservaProf.getProfesor());
				reservaProfesorImp.deleteById(id);
			}

		}

		return "redirect:/clases";

	}

}
