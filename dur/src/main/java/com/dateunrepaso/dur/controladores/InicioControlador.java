package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class InicioControlador {
	
	@GetMapping("/app")
	public String app() {
		return "Index";
	}
	

}
