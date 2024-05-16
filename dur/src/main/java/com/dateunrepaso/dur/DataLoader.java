package com.dateunrepaso.dur;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Mensaje;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.AsignaturaRepo;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.repositorios.MensajeRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;

//@Configuration
public class DataLoader {

	@Bean
	CommandLineRunner initDatabase(AlumnoRepo alumnoRepository, AsignaturaRepo asignaturaRepository,
			AulaRepo aulaRepository, ProfesorRepo profesorRepository, MensajeRepo mensajeRepository,
			ReservaAlumnoRepo reservaAlumnoRepository, ReservaProfesorRepo reservaProfesorRepository) {
		return args -> {

//			// Crear Asignaturas
//			Asignatura matematicas = new Asignatura();
//			matematicas.setNombre("Matemáticas");
//			Asignatura historia = new Asignatura();
//			historia.setNombre("Historia");
//
//			asignaturaRepository.saveAll(Arrays.asList(matematicas, historia));
//
//			// Crear Aulas
//			Aula aula101 = new Aula();
//			aula101.setNombre("Aula 101");
//			aula101.setCantidadMaxAlumnos(30);
//			Aula aula102 = new Aula();
//			aula102.setNombre("Aula 102");
//			aula102.setCantidadMaxAlumnos(25);
//
//			aulaRepository.saveAll(Arrays.asList(aula101, aula102));
//
//			// Crear Alumnos
//			Alumno juan = new Alumno();
//			juan.setDni("12345678A");
//			juan.setNomCompleto("Juan Perez");
//			juan.setNomUsuario("juanp");
//			juan.setCorreo("juan@example.com");
//			juan.setContrasena("password123");
//			juan.setFechaNac("2000-01-01");
//			juan.setRol(Roles.ROL_ALUMNO);
//
//			Alumno maria = new Alumno();
//			maria.setDni("87654321B");
//			maria.setNomCompleto("Maria Gomez");
//			maria.setNomUsuario("mariag");
//			maria.setCorreo("maria@example.com");
//			maria.setContrasena("password123");
//			maria.setFechaNac("2001-02-02");
//			maria.setRol(Roles.ROL_ALUMNO);
//
//			alumnoRepository.saveAll(Arrays.asList(juan, maria));
//
//			// Crear Profesores
//			Profesor carlos = new Profesor();
//			carlos.setDni("23456789C");
//			carlos.setNomCompleto("Carlos Sanchez");
//			carlos.setNomUsuario("carloss");
//			carlos.setCorreo("carlos@example.com");
//			carlos.setContrasena("password123");
//			carlos.setFechaNac("1980-03-03");
//			carlos.setRol(Roles.ROL_PROFESOR);
//			carlos.setAsignatura(matematicas);
//
//			Profesor ana = new Profesor();
//			ana.setDni("98765432D");
//			ana.setNomCompleto("Ana Lopez");
//			ana.setNomUsuario("anal");
//			ana.setCorreo("ana@example.com");
//			ana.setContrasena("password123");
//			ana.setFechaNac("1975-04-04");
//			ana.setRol(Roles.ROL_PROFESOR);
//			ana.setAsignatura(historia);
//
//			profesorRepository.saveAll(Arrays.asList(carlos, ana));
//
//			// Crear Mensajes
//			Mensaje mensaje1 = new Mensaje();
//			mensaje1.setProfesor(carlos);
//			mensaje1.setAlumno(juan);
//
//			Mensaje mensaje2 = new Mensaje();
//			mensaje2.setProfesor(ana);
//			mensaje2.setAlumno(maria);
//
//			mensajeRepository.saveAll(Arrays.asList(mensaje1, mensaje2));
//
//			  // Crear Reservas de Alumnos
//            ReservaAlumno reservaAlumno1 = new ReservaAlumno();
//            reservaAlumno1.setAlumno(juan);
//            reservaAlumno1.setProfesor(carlos);
//            reservaAlumno1.setAula(aula101);
//            reservaAlumno1.setFechaReserva(LocalDate.of(2024, 5, 15));
//            reservaAlumno1.setHoraInicio(10);
//            reservaAlumno1.setHoraFin(12);
//
//            ReservaAlumno reservaAlumno2 = new ReservaAlumno();
//            reservaAlumno2.setAlumno(maria);
//            reservaAlumno2.setProfesor(ana);
//            reservaAlumno2.setAula(aula102);
//            reservaAlumno2.setFechaReserva(LocalDate.of(2024, 5, 16));
//            reservaAlumno2.setHoraInicio(14);
//            reservaAlumno2.setHoraFin(16);
//
//            reservaAlumnoRepository.saveAll(Arrays.asList(reservaAlumno1, reservaAlumno2));
//
//            // Crear Reservas de Profesores
//            ReservaProfesor reservaProfesor1 = new ReservaProfesor();
//            reservaProfesor1.setProfesor(carlos);
//            reservaProfesor1.setAula(aula101);
//            reservaProfesor1.setFechaReserva(LocalDate.of(2024, 5, 17));
//            reservaProfesor1.setHoraInicio(8);
//            reservaProfesor1.setHoraFin(10);
//
//            ReservaProfesor reservaProfesor2 = new ReservaProfesor();
//            reservaProfesor2.setProfesor(ana);
//            reservaProfesor2.setAula(aula102);
//            reservaProfesor2.setFechaReserva(LocalDate.of(2024, 5, 18));
//            reservaProfesor2.setHoraInicio(12);
//            reservaProfesor2.setHoraFin(14);
//            
//			reservaProfesorRepository.saveAll(Arrays.asList(reservaProfesor1, reservaProfesor2));
		};
	}
}
