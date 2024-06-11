package com.dateunrepaso.dur.utilidades;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UtilidadesString {
	
	public static String crearNombreUsuario(String nombreUsuario){
		
		String[] nombreSeparado = nombreUsuario.split(" ");
		
		return nombreSeparado[0] + nombreSeparado[0].charAt(0) + nombreSeparado[0].charAt(0);
	}

	public static boolean esMayorEdad(String fecha, int Edad){
		try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            LocalDate birthDate = LocalDate.parse(fecha, formatter);
            
            LocalDate currentDate = LocalDate.now();
            
            int age = Period.between(birthDate, currentDate).getYears();
            
            return age >= Edad;
            
        } catch (DateTimeParseException e) {
            return false;
        }
	}

}
