package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;

import jakarta.servlet.http.HttpSession;


@Controller
public class GestionUsuarioControlador {
    
    @GetMapping("/gestionUsuario")
    public String getGestionUsuario(HttpSession sesion, Model model) {
        if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
            Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", alumno);
            model.addAttribute("tipoUsuario", "alumno");
        } else {
            Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", profesor);
            model.addAttribute("tipoUsuario", "profesor");
        }

        return "GestionUsuario";
    }
    

}
