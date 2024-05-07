package com.dateunrepaso.dur.entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "IdProfesorFk", nullable = false)
	private Profesor profesor;

	@ManyToOne()
	@JoinColumn(name = "IdAlumnoFk", nullable = false)
	private Alumno alumno;

	public Mensaje() {
	}

	public Mensaje(Long id, Profesor profesor, Alumno alumno) {
		super();
		this.id = id;
		this.profesor = profesor;
		this.alumno = alumno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

}
