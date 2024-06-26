package com.dateunrepaso.dur.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.EmailServiceImp;
import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.AsignaturaImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;
import com.dateunrepaso.dur.utilidades.UtilidadesString;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("permitAll()")
public class LoginControlador {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	AsignaturaImp asignaturaImp;

	@Autowired
	ProfesorImp profesorImp;

	@Autowired
	AlumnoImp alumnoImp;

	@Autowired
	EmailServiceImp emailServiceImp;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String getLandingPage() {
		return "LandingPage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login";
	}

	@GetMapping("/registro")
	public String getRegistro(Model model) {
		model.addAttribute("listaAsignaturas", asignaturaImp.findAll());
		return "Registrarse";
	}

	@PostMapping("/registro")
	public String registrarUsuario(@RequestParam(name = "nomCompleto") String nomCompleto,
			@RequestParam(name = "nomUsuario") String nomUsuario,
			@RequestParam(name = "dni") String dni,
			@RequestParam(name = "fechaNac") String fechaNac,
			@RequestParam(name = "correo") String correo,
			@RequestParam(name = "contrasena") String contrasena,
			@RequestParam(name = "contrasenaRep") String contrasenaRep,
			@RequestParam(name = "perfil") String perfil,
			@RequestParam(name = "asignaturaProf") Long idAsig,
			HttpServletRequest request, RedirectAttributes atributos) {

		boolean correcto = false;

		List<Usuario> usuarios = usuarioService.findAllUsuarios();

		// Validacion basica
		if (!contrasena.equals(contrasenaRep)) {
			atributos.addFlashAttribute("Error", "Las contraseñas tienen que coincidir");
		} else if (usuarios.stream().anyMatch(u -> u.getNomUsuario().equals(nomUsuario))) {
			atributos.addFlashAttribute("Error", "El nombre de usuario indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
			atributos.addFlashAttribute("Error", "El correo indicado ya existe");
		} else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni))) {
			atributos.addFlashAttribute("Error", "El dni indicado ya existe");
		} else if (perfil.equals("esProfesor") && !UtilidadesString.esMayorEdad(fechaNac, 18)) {
			atributos.addFlashAttribute("Error", "Tienes que ser mayor de 18 años");
		} else if (perfil.equals("esAlumno") && !UtilidadesString.esMayorEdad(fechaNac, 8)) {
			atributos.addFlashAttribute("Error", "Tienes que ser mayor de 8 años");
		} else {
			correcto = true;
		}

		if (correcto) {
			Usuario usuario = null;

			if (perfil.equals("esProfesor")) {
				Profesor profesor = new Profesor(null, dni, nomCompleto, nomUsuario, correo,
						new BCryptPasswordEncoder().encode(contrasena), fechaNac,
						Roles.PROFESOR, asignaturaImp.findById(idAsig).get());
				profesorImp.save(profesor);
				usuario = profesor;
			} else {
				Alumno alumno = new Alumno(null, dni, nomCompleto, nomUsuario, correo,
						new BCryptPasswordEncoder().encode(contrasena), fechaNac,
						Roles.ALUMNO);
				alumnoImp.save(alumno);
				usuario = alumno;
			}

			// Autenticar al usuario
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					usuario.getNomUsuario(), contrasena);
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Crear sesión
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());

			// Mandar correo

			EmailDTO emailDTO = new EmailDTO(correo, "Gracias por registrarte en DateUnRepaso 🎉🎉", "<h2>Te has registrado en DateUnRepaso correctamente</h2><br>Usuario: "+ nomUsuario + "<br>Contraseña: " + contrasena);

			emailServiceImp.enviarCorreo(emailDTO);

			return "redirect:/app";
		} else {
			return "redirect:/registro";
		}
	}
}
