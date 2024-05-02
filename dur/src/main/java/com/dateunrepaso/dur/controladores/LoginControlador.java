package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginControlador {
	
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
}

