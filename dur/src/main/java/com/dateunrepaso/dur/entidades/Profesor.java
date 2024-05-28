package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.dateunrepaso.dur.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Profesor implements Serializable {

	private static final long serialVersionUID = 4L;

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

	@JsonIgnore
	@OneToMany(mappedBy = "profesor")
	private List<ReservaAlumno> reservas;

	@ManyToOne
	@JoinColumn(name = "idAsignatura", nullable = false)
	private Asignatura asignatura;
	// Constructores

	public Profesor() {
	}

	public Profesor(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac, Asignatura asignatura) {
		this.id = id;
		this.dni = dni;
		this.nomCompleto = nomCompleto;
		this.nomUsuario = nomUsuario;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fechaNac = fechaNac;
		this.asignatura = asignatura;
		this.rol = Roles.ROL_PROFESOR;
	}

//Getters and setters

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

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Long getId() {
		return id;
	}

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(asignatura, contrasena, correo, dni, fechaNac, id, nomCompleto, nomUsuario, reservas, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(asignatura, other.asignatura) && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correo, other.correo) && Objects.equals(dni, other.dni)
				&& Objects.equals(fechaNac, other.fechaNac) && Objects.equals(id, other.id)
				&& Objects.equals(nomCompleto, other.nomCompleto) && Objects.equals(nomUsuario, other.nomUsuario)
				&& Objects.equals(reservas, other.reservas) && rol == other.rol;
	}

}
