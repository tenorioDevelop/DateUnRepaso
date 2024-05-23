package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Aula implements Serializable {

	private static final long serialVersionUID = 4L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false, unique = true)
	private String nombre;

	@Column(name = "cantidadMaximaAlumnos", nullable = false, unique = false)
	private int cantidadMaxAlumnos;

	@JsonIgnore
	@OneToMany(mappedBy = "aula")
	private List<ReservaAlumno> reservas;

	public Aula() {
	}

	public Aula(Long id, String nombre, int cantidadMaxAlumnos, List<ReservaAlumno> reservas) {
		this.id = id;
		this.nombre = nombre;
		this.cantidadMaxAlumnos = cantidadMaxAlumnos;
		this.reservas = reservas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadMaxAlumnos() {
		return cantidadMaxAlumnos;
	}

	public void setCantidadMaxAlumnos(int cantidadMaxAlumnos) {
		this.cantidadMaxAlumnos = cantidadMaxAlumnos;
	}

	public List<ReservaAlumno> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaAlumno> reservas) {
		this.reservas = reservas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidadMaxAlumnos, id, nombre, reservas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return cantidadMaxAlumnos == other.cantidadMaxAlumnos && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(reservas, other.reservas);
	}

}
