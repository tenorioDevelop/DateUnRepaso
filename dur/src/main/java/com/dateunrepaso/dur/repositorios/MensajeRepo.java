package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Mensaje;

@Repository
public interface MensajeRepo extends JpaRepository<Mensaje, Long> {

}
