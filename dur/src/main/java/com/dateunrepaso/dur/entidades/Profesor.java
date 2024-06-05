package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;

import com.dateunrepaso.dur.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Profesor extends Usuario implements Serializable {

    private static final long serialVersionUID = 4L;

    @JsonIgnore
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaProfesor> reservas;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAsignatura", nullable = false)
    private Asignatura asignatura;

    // Constructores

    public Profesor() {
        super();
    }

    public Profesor(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
    String fechaNac, Roles rol, Asignatura asignatura) {
        super(id, dni, nomCompleto, nomUsuario, correo, contrasena, fechaNac, rol);
        this.asignatura = asignatura;
    }

    // Getters and setters

    public List<ReservaProfesor> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaProfesor> reservas) {
        this.reservas = reservas;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
        result = prime * result + ((asignatura == null) ? 0 : asignatura.hashCode());
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
        Profesor other = (Profesor) obj;
        if (reservas == null) {
            if (other.reservas != null)
                return false;
        } else if (!reservas.equals(other.reservas))
            return false;
        if (asignatura == null) {
            if (other.asignatura != null)
                return false;
        } else if (!asignatura.equals(other.asignatura))
            return false;
        return true;
    }
}
