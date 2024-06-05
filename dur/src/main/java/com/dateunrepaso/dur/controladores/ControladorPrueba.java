package com.dateunrepaso.dur.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@PreAuthorize("denyAll()")
public class ControladorPrueba {

    @GetMapping("/prueba")
    @PreAuthorize("permitAll()")
    public String getMethodNameUno() {
        return "prueba";
    }

    @GetMapping("/prueba2")
    @PreAuthorize("hasAuthority('READ')")
    public String getMethodNameDos() {
        return "prueba";
    }

}
