package com.dateunrepaso.dur.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dateunrepaso.dur.entidades.Administrador;

@Repository
public interface AdminRepo extends JpaRepository<Administrador, Long> {

}
