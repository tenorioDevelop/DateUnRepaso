package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioControlador {
	@GetMapping("/app")
	@ResponseBody
	public String getApp() {
		return "hola";
	}

}
