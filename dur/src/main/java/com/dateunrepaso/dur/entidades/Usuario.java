package com.dateunrepaso.dur.entidades;

import com.dateunrepaso.dur.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Usuario {
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

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "fecha_nac", nullable = false)
    private String fechaNac;

    @Column(name = "rol", nullable = true)
    private Roles rol;

    public Usuario() {
    }

    public Usuario(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
            String fechaNac, Roles rol) {
        this.id = id;
        this.dni = dni;
        this.nomCompleto = nomCompleto;
        this.nomUsuario = nomUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaNac = fechaNac;
        this.rol = rol;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
        result = prime * result + ((nomCompleto == null) ? 0 : nomCompleto.hashCode());
        result = prime * result + ((nomUsuario == null) ? 0 : nomUsuario.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((contrasena == null) ? 0 : contrasena.hashCode());
        result = prime * result + ((fechaNac == null) ? 0 : fechaNac.hashCode());
        result = prime * result + ((rol == null) ? 0 : rol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dni == null) {
            if (other.dni != null)
                return false;
        } else if (!dni.equals(other.dni))
            return false;
        if (nomCompleto == null) {
            if (other.nomCompleto != null)
                return false;
        } else if (!nomCompleto.equals(other.nomCompleto))
            return false;
        if (nomUsuario == null) {
            if (other.nomUsuario != null)
                return false;
        } else if (!nomUsuario.equals(other.nomUsuario))
            return false;
        if (correo == null) {
            if (other.correo != null)
                return false;
        } else if (!correo.equals(other.correo))
            return false;
        if (contrasena == null) {
            if (other.contrasena != null)
                return false;
        } else if (!contrasena.equals(other.contrasena))
            return false;
        if (fechaNac == null) {
            if (other.fechaNac != null)
                return false;
        } else if (!fechaNac.equals(other.fechaNac))
            return false;
        if (rol != other.rol)
            return false;
        return true;
    }

    
}
