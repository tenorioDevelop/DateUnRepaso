package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Profesor;

@Repository
public interface ProfesorRepo extends JpaRepository<Profesor, Long> {

}
