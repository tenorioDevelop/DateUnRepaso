package com.dateunrepaso.dur.utilidades;

public class UtilidadesControladores {
	/**
	 * 
	 * @param correo es resultado de poner sesion.getAttribute("usuarioLogeado")
	 *
	 * @return TRUE si el usuario esta registrado o FALSE en caso contrario
	 * */
	public static boolean usuarioEstaRegistrado(Object correo) {
		return correo == null;
	}

}
