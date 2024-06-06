package com.dateunrepaso.dur.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@PreAuthorize("denyAll()")
public class ControladorPrueba {

    @GetMapping("/profesores")
    @PreAuthorize("hasRole('PROFESOR')")
    public String getMethodNameUno() {
        return "PROFESORES";
    }

    @GetMapping("/alumnos")
    @PreAuthorize("hasRole('ALUMNO')")
    public String getMethodNameDos() {
        return "ALUMNOS";
    }

}
