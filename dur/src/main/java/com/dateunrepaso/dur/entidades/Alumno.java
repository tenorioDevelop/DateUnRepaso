package com.dateunrepaso.dur.entidades;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Alumno {
//	Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String dni;

	private String nomCompleto;

	private String nomUsuario;

	private String correo;

	private String contrasena;

	private String fechaNac;

	public Alumno() {
		super();
	}

	public Alumno(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac) {
		super();
		this.id = id;
		this.dni = dni;
		this.nomCompleto = nomCompleto;
		this.nomUsuario = nomUsuario;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fechaNac = fechaNac;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNomCompleto() {
		return nomCompleto;
	}

	public void setNomCompleto(String nomCompleto) {
		this.nomCompleto = nomCompleto;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	

}
