package com.dateunrepaso.dur.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class InicioControlador {
	@GetMapping("")
	@ResponseBody
	@PreAuthorize("hasRole('PROFESOR')")
	public String getApp() {
		return "hola";
	}

}
