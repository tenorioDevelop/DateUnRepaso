package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.servicios.UsuarioService;

@Controller
public class LoginControlador {

	@Autowired
	UsuarioService usuarioService;	

	@GetMapping("/")
	public String getLandingPage() {
		return "LandingPage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login";
	}

	@GetMapping("/registro")
	public String getRegistro() {
		return "Registrarse";
	}

	 @PostMapping("/registro")
    public String registrarUsuario(@RequestBody Usuario usuario) {
        // Lógica para registrar el usuario en tu base de datos
        // Aquí asumimos que userDetailsService tiene un método para crear un nuevo usuario
        // usuarioService.crearUsuario(usuario);

        // Autenticar al usuario manualmente después del registro
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.getNomUsuario(), usuario.getContrasena());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirigir al usuario a la página de la aplicación después del registro
        return "redirect:/app";
    }
}
