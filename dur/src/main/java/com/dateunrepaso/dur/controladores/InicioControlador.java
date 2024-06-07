package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;

@Controller
@RequestMapping("/app")
public class InicioControlador {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ReservaAlumnoImp reservaAlumnoImp;

	@Autowired
	AlumnoImp alumnoImp;

	@Autowired
	ReservaProfesorImp reservaProfesorImp;

	@Autowired
	ProfesorImp profesorImp;

	@GetMapping("")
	public String getApp(Model model) {
		model.addAttribute("paginaActiva", "inicio");
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		model.addAttribute("fechaActual", formato.format(LocalDate.now()));

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		Roles rol = usuario.getRol();

		model.addAttribute("usuario", usuario);
		model.addAttribute("tipoUsuario", rol.name().toLowerCase());

		if (rol == Roles.ALUMNO) {
			Alumno alumno = alumnoImp.findById(usuario.getId()).get();
			ReservaAlumno ultimaReserva;

			if (reservaAlumnoImp.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now()).isEmpty()) {
				ultimaReserva = null;
			} else {
				ultimaReserva = reservaAlumnoImp.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now())
						.get(reservaAlumnoImp.findAllByAlumnoAndFechaReserva(alumno, LocalDate.now()).size() - 1);
			}
			model.addAttribute("ultimaReserva", ultimaReserva);

		} else if (rol == Roles.PROFESOR) {
			Profesor profesor = profesorImp.findById(usuario.getId()).get();
			ReservaProfesor ultimaReserva;
			if (reservaProfesorImp.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).isEmpty()) {
				ultimaReserva = null;
			} else {
				ultimaReserva = reservaProfesorImp.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).get(
						reservaProfesorImp.findAllByProfesorAndFechaReserva(profesor, LocalDate.now()).size() - 1);
				int numeroAlumnos = reservaAlumnoImp.findByAulaAndFechaReservaAndProfesor(ultimaReserva.getAula(),
						ultimaReserva.getFechaReserva(), profesor).get().size();
				model.addAttribute("numeroAlumnos", numeroAlumnos);
			}
			model.addAttribute("ultimaReserva", ultimaReserva);

		}

		return "Index";
	}

}
