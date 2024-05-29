package com.dateunrepaso.dur.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.AulaImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.utilidades.UtilidadesControladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panel-admin")
public class AdminControlador {

	@Autowired
	AlumnoImp alumnoImp;

	@Autowired
	AulaImp aulaImp;

	@Autowired
	ReservaAlumnoImp reservaAlumnoImp;
	
	@Autowired
	ReservaProfesorImp reservaProfesorImp;

	@Autowired
	ProfesorImp profesorImp;

	@GetMapping({ "/", "" })
	public String getPanelAdm(Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		return "PanelADM";
	}

	@GetMapping("/alumnos")
	public String getAlumnosAdm(Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		List<Alumno> alumnos = alumnoImp.findAll();
		model.addAttribute("alumnos", alumnos);

		return "AlumnosADM";
	}

	@GetMapping("/alumnos/eliminar/{id}")
	public String getEliminarAlumno(@PathVariable Long id) {
		alumnoImp.deleteById(id);
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/profesores")
	public String getProfesoresAdm(Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		List<Profesor> profesores = profesorImp.findAll();
		model.addAttribute("profesores", profesores);

		return "ProfesoresADM";
	}

	@GetMapping("/profesores/eliminar/{id}")
	public String getEliminarProfesor(@PathVariable Long id) {
		profesorImp.deleteById(id);
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/aulas")
	public String getAulas(Model model, HttpSession sesion) {
		crearModel(model, sesion);

		model.addAttribute("aulas", aulaImp.findAll());

		return "AulasADM";
	}

	@GetMapping("/aulas/eliminar/{id}")
	public String getEliminarAula(@PathVariable Long id) {
		// Eliminar todas las reservas de profesores asociadas al aula
		List<ReservaProfesor> reservas = reservaProfesorImp.findByAulaId(id);
		reservaProfesorImp.deleteAll(reservas);

		// Eliminar el aula
		aulaImp.deleteById(id);
		return "redirect:/panel-admin/aulas";
	}

	public void crearModel(Model model, HttpSession sesion) {
		if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
			Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", alumno);
			model.addAttribute("tipoUsuario", "alumno");
		} else {
			Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", profesor);
			model.addAttribute("tipoUsuario", "profesor");
		}
	}

}
