package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Asignatura;

@Repository
public interface AsignaturaRepo extends JpaRepository<Asignatura, Long>{

}
