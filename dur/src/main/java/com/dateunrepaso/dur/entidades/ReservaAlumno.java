package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaAlumno implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "idAulaFK", nullable = false)
    private Aula aula;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_inicio", nullable = false)
    private Integer horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private Integer horaFin;

    @ManyToOne
    @JoinColumn(name = "idReservaProfesor", nullable = false)
    private ReservaProfesor reservaProfesor;

    // Constructors, getters, setters, hashCode, and equals methods

    public ReservaAlumno() {
    }

    public ReservaAlumno(Long id, Alumno alumno, Profesor profesor, Aula aula, LocalDate fechaReserva,
            Integer horaInicio, Integer horaFin) {
        this.id = id;
        this.alumno = alumno;
        this.profesor = profesor;
        this.aula = aula;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public ReservaAlumno(Long id, Alumno alumno, Profesor profesor, Aula aula, LocalDate fechaReserva,
            Integer horaInicio, Integer horaFin, ReservaProfesor reservaProfesor) {
        this.id = id;
        this.alumno = alumno;
        this.profesor = profesor;
        this.aula = aula;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.reservaProfesor = reservaProfesor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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

    public ReservaProfesor getReservaProfesor() {
        return reservaProfesor;
    }

    public void setReservaProfesor(ReservaProfesor reservaProfesor) {
        this.reservaProfesor = reservaProfesor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aula, fechaReserva, horaFin, horaInicio, id, alumno, profesor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReservaAlumno other = (ReservaAlumno) obj;
        return Objects.equals(aula, other.aula) && Objects.equals(fechaReserva, other.fechaReserva)
                && Objects.equals(horaFin, other.horaFin) && Objects.equals(horaInicio, other.horaInicio)
                && Objects.equals(id, other.id) && Objects.equals(alumno, other.alumno)
                && Objects.equals(profesor, other.profesor);
    }
}
