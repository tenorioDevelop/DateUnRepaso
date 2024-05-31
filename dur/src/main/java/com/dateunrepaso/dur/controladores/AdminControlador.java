package com.dateunrepaso.dur.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.AsignaturaImp;
import com.dateunrepaso.dur.servicios.AulaImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.utilidades.UtilidadesControladores;
import com.dateunrepaso.dur.utilidades.UtilidadesString;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel-admin")
public class AdminControlador {

	@Autowired
	AsignaturaImp asigImp;

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

	@GetMapping("/alumnos/crear")
	public String getCrearAlumnosAdm(Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		return "CrearAlumnoADM";
	}

	@PostMapping("/alumnos/crear")
	public String postsCrearAlumnosAdm(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			HttpSession sesion, Model model, RedirectAttributes atributos) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		boolean correcto = false;

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");

		} else if (!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty()) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

		} else if (profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			Alumno alumno = new Alumno(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo,
					contrasena, fechaNac);
			alumnoImp.save(alumno);
		}
		return "redirect:/panel-admin/alumnos/crear";
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

	@GetMapping("/profesores/crear")
	public String getCrearProfesoresAdm(Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}
		crearModel(model, sesion);

		model.addAttribute("listaAsignaturas", asigImp.findAll());

		return "CrearProfesorADM";
	}

	@PostMapping("/profesores/crear")
	public String postsCrearProfesoresAdm(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			HttpSession sesion, Model model, RedirectAttributes atributos) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		crearModel(model, sesion);

		boolean correcto = false;

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");

		} else if (!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty()) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

		} else if (profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			Alumno alumno = new Alumno(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo,
					contrasena, fechaNac);
			alumnoImp.save(alumno);
		}
		return "redirect:/panel-admin/alumnos/crear";
	}

	@Transactional
	@GetMapping("/profesores/eliminar/{id}")
	public String getEliminarProfesor(@PathVariable Long id) {
		List<ReservaAlumno> reservas = reservaAlumnoImp.findAllByProfesorId(id);
		if (!reservas.isEmpty()) {
			reservaAlumnoImp.deleteAll(reservas);
		}
		profesorImp.deleteById(id);
		return "redirect:/panel-admin/profesores";
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

	@GetMapping("/asignaturas")
	public String getAsignaturas(Model model, HttpSession sesion) {
		crearModel(model, sesion);

		List<Asignatura> asignaturas = asigImp.findAll();

		model.addAttribute("asignaturas", asignaturas);

		model.addAttribute("esProfesor", sesion.getAttribute("usuarioLogeado").getClass() == Profesor.class);

		return "AsignaturasADM";
	}

	@Transactional
	@GetMapping("/asignaturas/eliminar/{id}")
	public String getEliminarAsignatura(@PathVariable Long id) {
		Asignatura asignatura = asigImp.findById(id).get();

		// Borrar todas las reservasprofesores cuyos profesores dan esa asignatura
		for (Profesor profesor : asignatura.getProfesores()) {
			for (ReservaProfesor reservaProfesor : reservaProfesorImp.findAllByProfesor(profesor)) {
				reservaAlumnoImp.deleteAllByAulaAndFechaReservaAndProfesor(reservaProfesor.getAula(),
						reservaProfesor.getFechaReserva(), reservaProfesor.getProfesor());
			}
		}

		// Borrar todos los profesores que tienen esa asigantura
		profesorImp.deleteAllByAsignatura(asignatura);
		return "redirect:/panel-admin/asignaturas";
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
