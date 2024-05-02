package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Alumno;

@Repository
public interface AlumnoRepo extends JpaRepository<Alumno, Long>{
	
}
