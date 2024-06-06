package com.dateunrepaso.dur;

import javax.management.relation.Role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;

@SpringBootApplication(scanBasePackages = "com.dateunrepaso")
public class DurApplication {

	public static void main(String[] args) {
		SpringApplication.run(DurApplication.class, args);
	}

	/* @Bean
	CommandLineRunner init(AlumnoImp alumnoImp, ProfesorImp profesorImp){
		return args -> {
			Alumno alumno = new Alumno(null, "1234juan", "juan perez", "juanjj", "juan@gmail.com", "juan@gmail.com", "01-01-2000", Roles.ALUMNO);
			Profesor profesor = new Profesor(null, "1234carlos", "carlos sanchez", "carloscc", "carlos@gmail.com", "carlos@gmail.com", "01-01-2000", Roles.PROFESOR, null);
			Asignatura asig = new Asignatura();
			asig.setNombre("mates");
			profesor.setAsignatura(asig);
			alumnoImp.save(alumno);
			profesorImp.save(profesor);
		};
	} */

}
	