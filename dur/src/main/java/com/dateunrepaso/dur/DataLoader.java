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
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.AsignaturaRepo;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;
import com.dateunrepaso.dur.repositorios.ReservaAlumnoRepo;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;

// @Configuration
public class DataLoader {

	@Bean
	CommandLineRunner initDatabase(AlumnoRepo alumnoRepository, AsignaturaRepo asignaturaRepository,
			AulaRepo aulaRepository, ProfesorRepo profesorRepository,
			ReservaAlumnoRepo reservaAlumnoRepository, ReservaProfesorRepo reservaProfesorRepository) {
		return args -> {

			// Crear Aulas
			Aula aula1 = new Aula(null, "Aula 101", 30, null);
			Aula aula2 = new Aula(null, "Aula 102", 25, null);
			Aula aula3 = new Aula(null, "Aula 103", 30, null);
			Aula aula4 = new Aula(null, "Aula 104", 20, null);
			Aula aula5 = new Aula(null, "Aula 105", 25, null);
			Aula aula6 = new Aula(null, "Aula 106", 30, null);

			aulaRepository.save(aula1);
			aulaRepository.save(aula2);
			aulaRepository.save(aula3);
			aulaRepository.save(aula4);
			aulaRepository.save(aula5);
			aulaRepository.save(aula6);

			// Crear Asignaturas
			Asignatura asignatura1 = new Asignatura(null, "Matemáticas", null);
			Asignatura asignatura2 = new Asignatura(null, "Historia", null);
			Asignatura asignatura3 = new Asignatura(null, "Lengua", null);
			Asignatura asignatura4 = new Asignatura(null, "Inglés", null);
			Asignatura asignatura5 = new Asignatura(null, "Francés", null);
			Asignatura asignatura6 = new Asignatura(null, "Física", null);
			Asignatura asignatura7 = new Asignatura(null, "Química", null);

			asignaturaRepository.save(asignatura1);
			asignaturaRepository.save(asignatura2);
			asignaturaRepository.save(asignatura3);
			asignaturaRepository.save(asignatura4);
			asignaturaRepository.save(asignatura5);
			asignaturaRepository.save(asignatura6);
			asignaturaRepository.save(asignatura7);

		};
	}
}
