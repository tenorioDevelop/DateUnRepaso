package com.dateunrepaso.dur.utilidades;

public class UtilidadesString {
	
	public static String crearNombreUsuario(String nombreUsuario){
		
		String[] nombreSeparado = nombreUsuario.split(" ");
		
		return nombreSeparado[0] + nombreSeparado[0].charAt(0) + nombreSeparado[0].charAt(0);
	}

}
