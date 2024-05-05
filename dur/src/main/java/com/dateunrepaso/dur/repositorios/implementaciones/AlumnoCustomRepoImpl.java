package com.dateunrepaso.dur.repositorios.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;

import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.customizados.AlumnoCustomRepo;

public class AlumnoCustomRepoImpl implements AlumnoCustomRepo {
	
	@Autowired AlumnoRepo alumnoRepo;

	@Override
	public boolean existeAlumnoCorreo(String correo) {
		return false;
	}

}
