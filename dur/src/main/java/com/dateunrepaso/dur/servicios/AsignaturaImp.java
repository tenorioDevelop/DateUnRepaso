package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.repositorios.AsignaturaRepo;

@Service
public class AsignaturaImp {

    @Autowired
    AsignaturaRepo asignaturaRepo;

    @Transactional
    public void actualiarAsignatura(Long id, String nombre) {
        asignaturaRepo.updateAsignatura(id, nombre);
    }

    @Transactional
    public void delete(Asignatura asignatura) {
        asignaturaRepo.deleteById(asignatura.getId());
    }

    public Optional<Asignatura> findByNombre(String nombre) {
        return asignaturaRepo.findByNombre(nombre);
    }

    public List<Asignatura> findAll() {
        return asignaturaRepo.findAll();
    }

    public <S extends Asignatura> S save(S entity) {
        return asignaturaRepo.save(entity);
    }

    public Optional<Asignatura> findById(Long id) {
        return asignaturaRepo.findById(id);
    }

}
