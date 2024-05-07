package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.dateunrepaso.dur.utilidades.UtilidadesControladores;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioControlador {

	@GetMapping("/app")
	public String getApp(HttpSession sesion, Model model) {

		if (UtilidadesControladores.usuarioEstaRegistrado(sesion.getAttribute("usuarioLogeado"))) {
			return "redirect:/";
		}

		model.addAttribute("usuario", sesion.getAttribute("usuarioLogeado"));
		return "Index";
	}

}
