package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Asignatura implements Serializable {

	private static final long serialVersionUID = 2L;

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false, unique = true)
	private String nombre;

	@OneToMany(mappedBy = "asignatura")
	private List<Profesor> profesores;

	// Constructor

	public Asignatura() {
	}

	public Asignatura(Long idAsignatura, String nombre, List<Profesor> profesores) {
		this.id = idAsignatura;
		this.nombre = nombre;
		this.profesores = profesores;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long idAsignatura) {
		this.id = idAsignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, profesores);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asignatura other = (Asignatura) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(profesores, other.profesores);
	}

}
