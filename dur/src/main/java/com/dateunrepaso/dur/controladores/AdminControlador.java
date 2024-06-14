package com.dateunrepaso.dur.controladores;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.AsignaturaImp;
import com.dateunrepaso.dur.servicios.AulaImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;
import com.dateunrepaso.dur.utilidades.UtilidadesString;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequestMapping("/panel-admin")
public class AdminControlador {

	@Autowired
	AsignaturaImp asignaturaImp;

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

	@Autowired
	UsuarioService usuarioService;

	@GetMapping({ "/", "" })
	public String getPanelAdm(Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		return "PanelADM";
	}

	@GetMapping("/alumnos")
	public String getAlumnosAdm(Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		List<Alumno> alumnos = alumnoImp.findAll();
		model.addAttribute("alumnos", alumnos);

		return "AlumnosADM";
	}

	@GetMapping("/alumnos/crear")
	public String getCrearAlumnosAdm(Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		return "CrearAlumnoADM";
	}

	@PostMapping("/alumnos/crear")
	public String postsCrearAlumnosAdm(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "nomUsuario") String nomUsuario,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep, Model model, RedirectAttributes atributos) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		List<Usuario> usuarios = usuarioService.findAllUsuarios();
		boolean correcto = false;

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
		} else if (usuarios.stream().anyMatch(u -> u.getNomUsuario().equals(nomUsuario))) {
			atributos.addFlashAttribute("Error", "El nombre de usuario indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
			atributos.addFlashAttribute("Error", "El correo indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni))) {
			atributos.addFlashAttribute("Error", "El dni indicado ya existe");
		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno tiene que ser mayor de 8 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			Alumno alumno = new Alumno(null, dni, nombre, nomUsuario, correo,
					encriptarContrasenia(contrasena), fechaNac, Roles.ALUMNO);
			alumnoImp.save(alumno);
		}
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/alumnos/eliminar/{id}")
	public String getEliminarAlumno(@PathVariable Long id) {
		alumnoImp.deleteById(id);
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/alumnos/editar/{id}")
	public String getEditarAsignatura(@PathVariable Long id, Model model,
			RedirectAttributes atributos) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

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
			@RequestParam(name = "id") Long id, Model model,
			RedirectAttributes atributos) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		Alumno alumnoOriginal = alumnoImp.findById(id).get();
		List<Usuario> usuarios = usuarioService.findAllUsuarios();
		model.addAttribute("usuario", usuario);

		boolean correcto = false;

		// Validaciones

		// Validacion basica
		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
		} else if (usuarios.stream().anyMatch(u -> u.getNomUsuario().equals(nomUsuario))
				&& !nomUsuario.equals(alumnoOriginal.getNomUsuario())) {
			atributos.addFlashAttribute("Error", "El nombre de usuario indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))
				&& !correo.equals(alumnoOriginal.getCorreo())) {
			atributos.addFlashAttribute("Error", "El correo indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni)) && !dni.equals(alumnoOriginal.getDni())) {
			atributos.addFlashAttribute("Error", "El dni indicado ya existe");
		} else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "El alumno tiene que ser mayor de 8 años");
		} else {
			correcto = true;
		}

		if (correcto == true) {
			alumnoImp.actualizarAlumno(id, nombre, nomUsuario, dni, correo,
					encriptarContrasenia(contrasena), fechaNac);
		} else {
			return "redirect:/panel-admin/alumnos/editar/" + id;
		}
		return "redirect:/panel-admin/alumnos";
	}

	@GetMapping("/profesores")
	public String getProfesoresAdm(Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		List<Profesor> profesores = profesorImp.findAll();
		model.addAttribute("profesores", profesores);

		return "ProfesoresADM";
	}

	@GetMapping("/profesores/crear")
	public String getCrearProfesoresAdm(Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		model.addAttribute("listaAsignaturas", asigImp.findAll());

		return "CrearProfesorADM";
	}

	@PostMapping("/profesores/crear")
	public String postsCrearProfesoresAdm(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "nomUsuario") String nomUsuario,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "asignaturaProf") Long idAsig,
			Model model, RedirectAttributes atributos) {

		List<Usuario> usuarios = usuarioService.findAllUsuarios();
		boolean correcto = false;

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
		} else if (usuarios.stream().anyMatch(u -> u.getNomUsuario().equals(nomUsuario))) {
			atributos.addFlashAttribute("Error", "El nombre de usuario indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
			atributos.addFlashAttribute("Error", "El correo indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni))) {
			atributos.addFlashAttribute("Error", "El dni indicado ya existe");
		} else if (!UtilidadesString.esMayorEdad(fechaNac, 18)) {
			atributos.addFlashAttribute("Error", "El profesor tiene que ser mayor de 18 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			Profesor profesor = new Profesor(null, dni, nombre, nomUsuario, correo,
					encriptarContrasenia(contrasena), fechaNac, Roles.PROFESOR, asigImp.findById(idAsig).get());
			profesorImp.save(profesor);
		}
		return "redirect:/panel-admin/profesores";
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
	public String getEditarProfesorAdm(@PathVariable Long id, Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

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
			@RequestParam(name = "id") Long id, Model model,
			RedirectAttributes atributos) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		List<Usuario> usuarios = usuarioService.findAllUsuarios();
		model.addAttribute("usuario", usuario);

		boolean correcto = false;

		Profesor profesorOriginal = profesorImp.findById(id).get();

		// Validaciones

		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
		} else if (usuarios.stream().anyMatch(u -> u.getNomUsuario().equals(nomUsuario))
				&& !nomUsuario.equals(profesorOriginal.getNomUsuario())) {
			atributos.addFlashAttribute("Error", "El nombre de usuario indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))
				&& !correo.equals(profesorOriginal.getCorreo())) {
			atributos.addFlashAttribute("Error", "El correo indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni)) && !dni.equals(profesorOriginal.getDni())) {
			atributos.addFlashAttribute("Error", "El dni indicado ya existe");
		} else if (!UtilidadesString.esMayorEdad(fechaNac, 18)) {
			atributos.addFlashAttribute("Error", "El profesor tiene que ser mayor de 18 años");
		} else {
			correcto = true;
		}

		// Fin validaciones

		if (correcto == true) {
			profesorImp.actualizarProfesor(id, nombre, nomUsuario, correo,
					encriptarContrasenia(contrasena), fechaNac, dni, asigImp.findById(asignaturaProf).get());
		} else {
			return "redirect:/panel-admin/profesores/editar/" + id;
		}
		return "redirect:/panel-admin/profesores";
	}

	@GetMapping("/aulas")
	public String getAulas(Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		model.addAttribute("aulas", aulaImp.findAll());

		return "AulasADM";
	}

	@GetMapping("/aulas/crear")
	public String getCrearAula(Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		return "CrearAulaADM";
	}

	@PostMapping("/aulas/crear")
	public String postCrearAula(@RequestParam(name = "nombreAula") String nombre,
			@RequestParam(name = "cantMaxAlumn") Integer numAlumnos, RedirectAttributes atributos, Model model) {

		if (aulaImp.findByNombre(nombre).isEmpty()) {
			Aula aula = new Aula(null, nombre, numAlumnos, null);
			aulaImp.save(aula);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe un aula con este nombre");
		}

		return "redirect:/panel-admin/aulas";
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
	public String getEditarAula(@PathVariable Long id, Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		model.addAttribute("aula", aulaImp.findById(id).get());
		return "EditarAulaADM";
	}

	@PostMapping("/aulas/editar")
	public String postEditarAula(@RequestParam(name = "id") Long id,
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "cantidadMaxAlumnos") int cantidadMaxAlumnos,
			Model model, RedirectAttributes atributos) {

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
	public String getAsignaturas(Model model) {

		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);

		List<Asignatura> asignaturas = asigImp.findAll();

		model.addAttribute("asignaturas", asignaturas);

		model.addAttribute("esProfesor", usuario.getRol() == Roles.PROFESOR);

		return "AsignaturasADM";
	}

	@GetMapping("/asignaturas/crear")
	public String getCrearAsignatura(Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);
		return "CrearAsignaturaADM";
	}

	@PostMapping("/asignaturas/crear")
	public String postCrearAsignatura(@RequestParam(name = "nombreAsig") String nombre, RedirectAttributes atributos,
			Model model) {

		if (asigImp.findByNombre(nombre).isEmpty()) {
			Asignatura asig = new Asignatura(null, nombre, null);
			asigImp.save(asig);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe una asignatura con este nombre");
		}

		return "redirect:/panel-admin/asignaturas";
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

		asignaturaImp.delete(asignatura);

		return "redirect:/panel-admin/asignaturas";
	}

	@GetMapping("/asignaturas/editar/{id}")
	public String getEditarAsignautura(@PathVariable Long id, Model model) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
		model.addAttribute("usuario", usuario);
		model.addAttribute("asignatura", asigImp.findById(id).get());
		return "EditarAsignaturaADM";
	}

	@PostMapping("/asignaturas/editar")
	public String postEditarAsignautura(@RequestParam(name = "id") Long id,
			@RequestParam(name = "nombre") String nombre,
			Model model, RedirectAttributes atributos) {

		Asignatura asigOriginal = asigImp.findById(id).get();

		if (asigImp.findByNombre(nombre).isEmpty() || asigOriginal.getNombre().equals(nombre)) {
			asigImp.actualiarAsignatura(id, nombre);
		} else {
			atributos.addFlashAttribute("Error", "Ya existe una asignatura con este nombre");
			return "redirect:/panel-admin/asignaturas/editar/" + id;
		}

		return "redirect:/panel-admin/asignaturas";
	}

	@PostMapping("/confirmar-eliminar")
	public String postMethodName(@RequestParam String ruta, @RequestParam(required = false) String mensaje,
			@RequestParam String r,
			Model model) {
		if (r.equals("s")) {
			return "redirect:".concat(ruta);
		} else if (r.equals("n")) {
			return "redirect:/panel-admin";
		} else {
			String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
			model.addAttribute("usuario", usuario);

			model.addAttribute("ruta", ruta);
			model.addAttribute("mensaje", mensaje);

			return "ConfirmarEliminarADM";
		}
	}

	private String encriptarContrasenia(String contra) {
		return new BCryptPasswordEncoder().encode(contra);
	}

}
