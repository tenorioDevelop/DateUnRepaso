package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.IEmailService;
import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactoControlador {

    @Autowired
    IEmailService iEmailService;

    @GetMapping("/contacto")
    public String getContacto(HttpSession sesion, Model model) {
        crearModel(model, sesion);
        model.addAttribute("paginaActiva", "contacto");
        return "Contacto";
    }

    @PostMapping("/mandarcorreo")
    public void postMethodName(@RequestBody EmailDTO email) {
        iEmailService.enviarCorreo(email);
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
