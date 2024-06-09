package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.IEmailService;
import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
@RequestMapping("/contacto")
public class ContactoControlador {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    IEmailService iEmailService;

    @GetMapping("")
    public String getContacto(Model model) {
        model.addAttribute("paginaActiva", "contacto");
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        Roles rol = usuario.getRol();

        model.addAttribute("usuario", usuario);
        model.addAttribute("tipoUsuario", rol.name().toLowerCase());
        model.addAttribute("emailDTO", new EmailDTO());
        return "Contacto";
    }

    @PostMapping("/mandarcorreo")
    public String postMethodName(@ModelAttribute EmailDTO emailDTO) {
        iEmailService.enviarCorreo(emailDTO);
        return "redirect:/contacto"; // Redirect to avoid form resubmission
    }
}
