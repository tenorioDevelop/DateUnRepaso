package com.dateunrepaso.dur.entidades;

import java.sql.Date;


import com.dateunrepaso.dur.enums.Roles;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Alumno {
//	Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dni", unique = true, nullable = false)
	private String dni;

	@Column(name = "nom_completo", nullable = false)
	private String nomCompleto;

	@Column(name = "nom_usuario", unique = true, nullable = false)
	private String nomUsuario;

	@Column(name = "correo", unique = true, nullable = false)
	private String correo;

	@Column(name = "contrasena", unique = false, nullable = false)
	private String contrasena;

	@Column(name = "fecha_nac", unique = false, nullable = false)
	private String fechaNac;

	@Column(name = "rol", nullable = true)
	private Roles rol;

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

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

}
