package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.repositorios.AulaRepo;

@Service
public class AulaImp {

    @Autowired
    AulaRepo aulaRepo;

    @Transactional
    public void actualizarAula(Long id, int cantidadMaxAlumnos, String nombre) {
        aulaRepo.updateAula(id, cantidadMaxAlumnos, nombre);
    }

    public Optional<Aula> findByNombre(String nombre) {
        return aulaRepo.findByNombre(nombre);
    }

    public List<Aula> findAll() {
        return aulaRepo.findAll();
    }

    public <S extends Aula> S save(S entity) {
        return aulaRepo.save(entity);
    }

    public Optional<Aula> findById(Long id) {
        return aulaRepo.findById(id);
    }

    public void deleteById(Long id) {
        aulaRepo.deleteById(id);
    }

}
