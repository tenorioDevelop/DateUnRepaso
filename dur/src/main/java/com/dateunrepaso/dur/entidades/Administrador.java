package com.dateunrepaso.dur.entidades;

import java.io.Serializable;

import com.dateunrepaso.dur.enums.Roles;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario implements Serializable {

	public Administrador() {
	}

	public Administrador(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac) {
		super(id, dni, nomCompleto, nomUsuario, correo, contrasena, fechaNac, Roles.ADMINISTRADOR);
	}

}
