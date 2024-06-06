package com.dateunrepaso.dur.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Aula;
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

import com.dateunrepaso.dur.enums.Roles;

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
					contrasena, fechaNac, Roles.ALUMNO);
			alumnoImp.save(alumno);
		}
		return "redirect:/panel-admin/alumnos/crear";
	}

	@GetMapping("/alumnos/eliminar/{id}")
	public String getEliminarAlumno(@PathVariable Long id) {
		alumnoImp.deleteById(id);
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/alumnos/editar/{id}")
	public String getEditarAsignatura(@PathVariable Long id, Model model, HttpSession sesion,
			RedirectAttributes atributos) {
		crearModel(model, sesion);

		Optional<Alumno> alumno = alumnoImp.findById(id);

		if (alumno.isPresent()) {
			model.addAttribute("alumno", alumno.get());
		} else {
			return "redirect:/panel-admin/alumnos";
		}

		return "EditarAlumnoADM";
	}

	@PostMapping("/alumnos/editar")
	public String postEditarAlumno(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo,
			@RequestParam(name = "nomUsuario") String nomUsuario,
			@RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "id") Long id, Model model, HttpSession sesion,
			RedirectAttributes atributos) {
		crearModel(model, sesion);
		boolean correcto = false;

		Alumno alumnoOriginal = alumnoImp.findById(id).get();

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");

		} else if ((!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty())
				&& !correo.equals(alumnoOriginal.getCorreo())) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

		} else if ((profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null)
				&& !dni.equals(alumnoOriginal.getDni())) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			alumnoImp.actualizarAlumno(id, nombre, nomUsuario, dni, correo,
					contrasena, fechaNac);
		} else {
			return "redirect:/panel-admin/alumnos/editar/" + id;
		}
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
			@RequestParam(name = "asignaturaProf") Long idAsig,
			HttpSession sesion, Model model, RedirectAttributes atributos) {

		boolean correcto = false;

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");

		} else if (!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty()) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

		} else if (profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

		} else if (!UtilidadesString.esMayorEdad(fechaNac, 18)) {
			atributos.addFlashAttribute("Error", "No puedes ser menor de edad");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			Profesor profesor = new Profesor(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo,
					contrasena, fechaNac,Roles.PROFESOR, asigImp.findById(idAsig).get());
			profesorImp.save(profesor);
		}
		return "redirect:/panel-admin/profesores/crear";
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

	@GetMapping("/profesores/editar/{id}")
	public String getEditarProfesorAdm(@PathVariable Long id, Model model, HttpSession sesion) {
		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}
		crearModel(model, sesion);
		model.addAttribute("profesor", profesorImp.findById(id).get());
		model.addAttribute("asignaturas", asigImp.findAll());

		return "EditarProfesorADM";
	}

	@PostMapping("/profesores/editar")
	public String postEditarProfesor(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo,
			@RequestParam(name = "nomUsuario") String nomUsuario,
			@RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "asignaturaProf") Long asignaturaProf,
			@RequestParam(name = "id") Long id, Model model, HttpSession sesion,
			RedirectAttributes atributos) {
		crearModel(model, sesion);
		boolean correcto = false;

		Profesor profesorOriginal = profesorImp.findById(id).get();

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");

		} else if ((!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty())
				&& !correo.equals(profesorOriginal.getCorreo())) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

		} else if ((profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null)
				&& !dni.equals(profesorOriginal.getDni())) {
			atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			profesorImp.actualizarProfesor(id, nombre, nomUsuario, correo,
					contrasena, fechaNac, dni, asigImp.findById(asignaturaProf).get());
		} else {
			return "redirect:/panel-admin/profesores/editar/" + id;
		}
		return "redirect:/panel-admin/profesores";
	}

	@GetMapping("/aulas")
	public String getAulas(Model model, HttpSession sesion) {
		crearModel(model, sesion);

		model.addAttribute("aulas", aulaImp.findAll());

		return "AulasADM";
	}

	@GetMapping("/aulas/crear")
	public String getCrearAula(Model model, HttpSession sesion) {
		crearModel(model, sesion);
		return "CrearAulaADM";
	}

	@PostMapping("/aulas/crear")
	public String postCrearAula(@RequestParam(name = "nombreAula") String nombre,
			@RequestParam(name = "cantMaxAlumn") Integer numAlumnos, RedirectAttributes atributos, Model model,
			HttpSession sesion) {

		if (aulaImp.findByNombre(nombre).isEmpty()) {
			Aula aula = new Aula(null, nombre, numAlumnos, null);
			aulaImp.save(aula);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe un aula con este nombre");
		}

		return "redirect:/panel-admin/aulas/crear";
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

	@GetMapping("/aulas/editar/{id}")
	public String getEditarAula(@PathVariable Long id, Model model, HttpSession sesion) {
		crearModel(model, sesion);
		model.addAttribute("aula", aulaImp.findById(id).get());
		return "EditarAulaADM";
	}

	@PostMapping("/aulas/editar")
	public String postEditarAula(@RequestParam(name = "id") Long id,
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "cantidadMaxAlumnos") int cantidadMaxAlumnos,
			Model model, HttpSession sesion, RedirectAttributes atributos) {

		Aula aulaOriginal = aulaImp.findById(id).get();

		if (aulaImp.findByNombre(nombre).isEmpty() || aulaOriginal.getNombre().equals(nombre)) {
			aulaImp.actualizarAula(id, cantidadMaxAlumnos, nombre);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe un aula con este nombre");
			return "redirect:/panel-admin/aulas/editar/" + id;
		}

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

	@GetMapping("/asignaturas/crear")
	public String getCrearAsignatura(Model model, HttpSession sesion) {
		crearModel(model, sesion);
		return "CrearAsignaturaADM";
	}

	@PostMapping("/asignaturas/crear")
	public String postCrearAsignatura(@RequestParam(name = "nombreAsig") String nombre, RedirectAttributes atributos,
			Model model, HttpSession sesion) {

		if (asigImp.findByNombre(nombre).isEmpty()) {
			Asignatura asig = new Asignatura(null, nombre, null);
			asigImp.save(asig);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe una asignatura con este nombre");
		}

		return "redirect:/panel-admin/asignaturas/crear";
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

	@GetMapping("/asignaturas/editar/{id}")
	public String getEditarAsignautura(@PathVariable Long id, Model model, HttpSession sesion) {
		crearModel(model, sesion);
		model.addAttribute("asignatura", asigImp.findById(id).get());
		return "EditarAsignaturaADM";
	}

	@PostMapping("/asignaturas/editar")
	public String postEditarAsignautura(@RequestParam(name = "id") Long id,
			@RequestParam(name = "nombre") String nombre,
			Model model, HttpSession sesion, RedirectAttributes atributos) {

		Asignatura asigOriginal = asigImp.findById(id).get();

		if (asigImp.findByNombre(nombre).isEmpty() || asigOriginal.getNombre().equals(nombre)) {
			asigImp.actualiarAsignatura(id, nombre);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe una asignatura con este nombre");
			return "redirect:/panel-admin/asignaturas/editar/" + id;
		}

		return "redirect:/panel-admin/asignaturas";
	}

}
