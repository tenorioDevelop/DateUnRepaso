package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;

import com.dateunrepaso.dur.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Alumno extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaAlumno> reservas;

	public Alumno() {
		super();
	}

	public Alumno(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac, Roles rol) {
		super(id, dni, nomCompleto, nomUsuario, correo, contrasena, fechaNac, rol);

	}

	public List<ReservaAlumno> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaAlumno> reservas) {
		this.reservas = reservas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		if (reservas == null) {
			if (other.reservas != null)
				return false;
		} else if (!reservas.equals(other.reservas))
			return false;
		return true;
	}

}
