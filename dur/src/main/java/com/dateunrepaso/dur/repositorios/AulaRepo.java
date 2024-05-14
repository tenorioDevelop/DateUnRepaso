package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Aula;

@Repository
public interface AulaRepo extends JpaRepository<Aula, Long> {

}
