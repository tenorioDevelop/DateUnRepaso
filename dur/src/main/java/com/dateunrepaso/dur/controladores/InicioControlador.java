package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;
import com.dateunrepaso.dur.utilidades.UtilidadesControladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioControlador {

	@Autowired
	ReservaAlumnoRepo reservaAlumnoRepo;
	
	@Autowired
	ReservaProfesorRepo reservaProfesorRepo;
	
	@Autowired
	AlumnoRepo alumnoRepo;

	@GetMapping("/app")
	public String getApp(HttpSession sesion, Model model) {

		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}
		
		if(sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
			Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", alumno);
//			model.addAttribute("siguienteClase", reservaAlumnoRepo.findByAlumno(alumno));
			model.addAttribute("tipoUsuario", "alumno");
		} else {
			Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
			model.addAttribute("usuario", profesor);
//			model.addAttribute("siguienteClase", reservaProfesorRepo.findByProfesor(profesor));
			model.addAttribute("tipoUsuario", "profesor");
		}

		
		return "Index";
	}

}
