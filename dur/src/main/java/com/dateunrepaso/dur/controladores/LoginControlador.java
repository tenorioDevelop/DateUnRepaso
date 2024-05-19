package com.dateunrepaso.dur.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.utilidades.UtilidadesString;

import jakarta.servlet.http.HttpSession;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.AsignaturaRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;

@Controller
public class LoginControlador {

	@Autowired
	private ProfesorRepo profesorRepo;

	@Autowired
	private AlumnoRepo alumnoRepo;

	@Autowired
	private ProfesorImp profesorImp;

	@Autowired
	private AlumnoImp alumnoImp;

	@Autowired
	private AsignaturaRepo asignaturaRepo;

	@GetMapping("/")
	public String getLandingPage() {
		return "LandingPage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login";
	}

	@PostMapping("/login")
	public String getInicioSesion(@RequestParam(name = "correoLogin") String correoLogin,
			@RequestParam(name = "contrasenaLogin") String contrasenaLogin, HttpSession sesion,
			RedirectAttributes atributos) {

		Alumno alumnoOpt = alumnoImp.findByCorreoAndContrasena(correoLogin, contrasenaLogin);
		Profesor profesorOpt = profesorImp.findByCorreoAndContrasena(correoLogin, contrasenaLogin);

		boolean existeUsuario = false;

		if (alumnoOpt != null) {
			existeUsuario = true;
			sesion.setAttribute("usuarioLogeado", alumnoOpt);
		} else if (profesorOpt != null) {
			existeUsuario = true;
			sesion.setAttribute("usuarioLogeado", profesorOpt);
		}

		if (existeUsuario == true) {
			return "redirect:/app";
		} else {
			atributos.addFlashAttribute("Error", "El usuario no existe");
			return "redirect:/login";

		}

	}

	@GetMapping("/registro")
	public String getRegistro(Model model) {
		List<Asignatura> asignaturas = asignaturaRepo.findAll();

		model.addAttribute("listaAsignaturas", asignaturas);

		return "Registrarse";
	}

	@PostMapping("/registro")
	public String getNewUsuario(@RequestParam(name = "nombreReg") String nombre,
			@RequestParam(name = "dniReg") String dni, @RequestParam(name = "fechaNacReg") String fechaNac,
			@RequestParam(name = "correoReg") String correo, @RequestParam(name = "contrasenaReg") String contrasena,
			@RequestParam(name = "contrasenaRepReg") String contrasenaRep,
			@RequestParam(name = "perfilSel") String perfil, @RequestParam(name = "asignaturaProf") Long idAsig,
			HttpSession sesion, Model model, RedirectAttributes atributos) {

		Alumno alumnoOpt = alumnoImp.findByCorreoAndDni(correo, dni);
		Profesor profesorOpt = profesorImp.findByCorreoAndDni(correo, dni);

		boolean correcto = true;

		if (alumnoOpt == null && profesorOpt == null) {
			if (contrasena.equals(contrasenaRep)) {

				if (perfil.equals("esProfesor")) {
					if (idAsig != -1) {
						Asignatura asignaturaProf = asignaturaRepo.findById(idAsig).get();

						Profesor profesor = new Profesor(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre),
								correo, contrasena, fechaNac, asignaturaProf);

						profesorRepo.save(profesor);

						sesion.setAttribute("usuarioLogeado", profesor);
					} else {
						correcto = false;
					}
				} else if (perfil.equals("esAlumno")) {

					Alumno alumno = new Alumno(null, dni, nombre, UtilidadesString.crearNombreUsuario(nombre), correo,
							contrasena, fechaNac, null, null);

					alumnoRepo.save(alumno);
					sesion.setAttribute("usuarioLogeado", alumno);

				}
			} else {
				correcto = false;

			}
		} else {
			correcto = false;
		}

		if (correcto == true) {
			return "redirect:/app";
		} else {
			if (!contrasena.equals(contrasenaRep)) {
				atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
			} else if (!profesorRepo.findByCorreo(correo).isEmpty() || !alumnoRepo.findByCorreo(correo).isEmpty()) {
				atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");
			} else if (profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null) {
				atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");
			} else if (perfil.equals("esProfesor") && idAsig == -1) {
				atributos.addFlashAttribute("Error", "Al ser profesor tienes que elegir una asignatura");
			}
			return "redirect:/registro";
		}

	}
}
