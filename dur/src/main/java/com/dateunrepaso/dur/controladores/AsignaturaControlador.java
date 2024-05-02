package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AsignaturaControlador {
	
    @GetMapping("/")
    public String getMethodName() {
        return "hola"; // Este nombre puede indicar una plantilla Thymeleaf o similar.
    }
}

