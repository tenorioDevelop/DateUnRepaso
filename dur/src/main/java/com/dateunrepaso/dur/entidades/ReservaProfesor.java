package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaProfesor implements Serializable {

	private static final long serialVersionUID = 7L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idProfesorFK", nullable = false, unique = true)
	private Profesor profesor;

	@ManyToOne
	@JoinColumn(name = "idAulaFK", nullable = false, unique = true)
	private Aula aula;

	@Column(name = "fecha", nullable = false, unique = true)
	private LocalDate fechaReserva;

	@Column(name = "horaInicio", nullable = false, unique = true)
	private Integer horaInicio;

	@Column(name = "horaFin", nullable = false, unique = true)
	private Integer horaFin;

	public ReservaProfesor() {
	}

	public ReservaProfesor(Long id, Profesor profesor, Aula aula, LocalDate fechaReserva, Integer horaInicio,
			Integer horaFin) {
		this.id = id;
		this.profesor = profesor;
		this.aula = aula;
		this.fechaReserva = fechaReserva;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public Integer getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Integer horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Integer getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Integer horaFin) {
		this.horaFin = horaFin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aula, fechaReserva, horaFin, horaInicio, profesor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservaProfesor other = (ReservaProfesor) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(fechaReserva, other.fechaReserva)
				&& Objects.equals(horaFin, other.horaFin) && Objects.equals(horaInicio, other.horaInicio)
				&& Objects.equals(profesor, other.profesor);
	}

}
