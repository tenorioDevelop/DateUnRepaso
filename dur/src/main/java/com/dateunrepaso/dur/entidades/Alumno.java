package com.dateunrepaso.dur.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.dateunrepaso.dur.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dni", unique = true, nullable = false, length = 20)
	private String dni;

	@Column(name = "nom_completo", nullable = false, length = 100)
	private String nomCompleto;

	@Column(name = "nom_usuario", unique = true, nullable = false, length = 50)
	private String nomUsuario;

	@Column(name = "correo", unique = true, nullable = false, length = 100)
	private String correo;

	@Column(name = "contrasena", nullable = false, length = 100)
	private String contrasena;

	@Column(name = "fecha_nac", nullable = false, length = 10)
	private String fechaNac;

	@Column(name = "rol", nullable = true)
	private Roles rol;

	@JsonIgnore
	@OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaAlumno> reservas;

	public Alumno() {
		super();
	}

	public Alumno(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac) {
		this.id = id;
		this.dni = dni;
		this.nomCompleto = nomCompleto;
		this.nomUsuario = nomUsuario;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fechaNac = fechaNac;
		this.rol = Roles.ROL_ALUMNO;
	}

	public Alumno(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
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

	public Alumno(Long id, String dni, String nomCompleto, String nomUsuario, String correo, String contrasena,
			String fechaNac, List<ReservaAlumno> reservas) {
		this(id, dni, nomCompleto, nomUsuario, correo, contrasena, fechaNac, Roles.ROL_ALUMNO);
		this.reservas = reservas;
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

	public List<ReservaAlumno> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaAlumno> reservas) {
		this.reservas = reservas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, dni, fechaNac, id, nomCompleto, nomUsuario, reservas, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(correo, other.correo)
				&& Objects.equals(dni, other.dni) && Objects.equals(fechaNac, other.fechaNac)
				&& Objects.equals(id, other.id) && Objects.equals(nomCompleto, other.nomCompleto)
				&& Objects.equals(nomUsuario, other.nomUsuario) && Objects.equals(reservas, other.reservas)
				&& rol == other.rol;
	}
}
