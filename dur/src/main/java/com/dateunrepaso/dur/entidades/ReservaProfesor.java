package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "idProfesorFK", "idAulaFK", "fecha", "horaInicio", "horaFin" }) })
public class ReservaProfesor implements Serializable {

	private static final long serialVersionUID = 7L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idProfesorFK", nullable = false, unique = false)
	private Profesor profesor;

	@ManyToOne
	@JoinColumn(name = "idAulaFK", nullable = false, unique = false)
	private Aula aula;

	@Column(name = "fecha", nullable = false, unique = false)
	private LocalDate fechaReserva;

	@Column(name = "horaInicio", nullable = false, unique = false)
	private Integer horaInicio;

	@Column(name = "horaFin", nullable = false, unique = false)
	private Integer horaFin;

	@OneToMany(mappedBy = "reservaProfesor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaAlumno> lstReservaAlumno;

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

	public ReservaProfesor(Long id, Profesor profesor, Aula aula, LocalDate fechaReserva, Integer horaInicio,
			Integer horaFin, List<ReservaAlumno> lstReservaAlumno) {
		this.id = id;
		this.profesor = profesor;
		this.aula = aula;
		this.fechaReserva = fechaReserva;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.lstReservaAlumno = lstReservaAlumno;
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

	public List<ReservaAlumno> getLstReservaAlumno() {
		return lstReservaAlumno;
	}

	public void setLstReservaAlumno(List<ReservaAlumno> lstReservaAlumno) {
		this.lstReservaAlumno = lstReservaAlumno;
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
