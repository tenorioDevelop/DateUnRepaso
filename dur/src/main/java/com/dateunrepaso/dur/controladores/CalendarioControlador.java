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
import org.springframework.web.bind.annotation.RequestParam;

import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.UsuarioService;

@Controller
@PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
@RequestMapping("/calendario")
public class CalendarioControlador {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public String getMethodName(Model model) {
        model.addAttribute("paginaActiva", "calendario");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        model.addAttribute("fechaActual", formato.format(LocalDate.now()));

        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        Roles rol = usuario.getRol();

        model.addAttribute("usuario", usuario);
        model.addAttribute("tipoUsuario", rol.name().toLowerCase());
        
        return "Calendario";
    }

}
