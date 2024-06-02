package com.dateunrepaso.dur.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Asignatura;
import com.dateunrepaso.dur.repositorios.AsignaturaRepo;

@Service
public class AsignaturaImp {

    @Autowired
    AsignaturaRepo asignaturaRepo;

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
