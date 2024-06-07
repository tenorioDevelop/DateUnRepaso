package com.dateunrepaso.dur.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;

@Service
public class ReservaProfesorImp {

    @Autowired
    ReservaProfesorRepo reservaProfesorRepo;

    @Autowired
    ProfesorImp profesorImp;

    /**
     * @param alumno
     * @param fecha
     * @return Las reservas del profesor indicado y la fecha indicada en adelante
     */
    public List<ReservaProfesor> getReservasDeProfesorPorFecha(Profesor profesor, LocalDate fecha) {
        return this.findAllByProfesor(profesor)
                .stream()
                .filter(p -> p.getFechaReserva().isEqual(fecha) || p.getFechaReserva().isAfter(fecha))
                .collect(Collectors.toList());
    }

    /**
     * @param fecha
     * @return Las reservas de todos los profesores y de la fecha indicada y
     *         adelante
     */
    public List<ReservaProfesor> getReservasDeProfesorActuales() {
        return this.findAll()
                .stream()
                .filter(p -> p.getFechaReserva().isEqual(LocalDate.now())
                        || p.getFechaReserva().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public void deleteAllByProfesor(Profesor profesor) {
        reservaProfesorRepo.deleteAllByProfesor(profesor);
    }

    public List<ReservaProfesor> findByAulaId(Long id) {
        return reservaProfesorRepo.findByAulaId(id);
    }

    public Optional<ReservaProfesor> findById(Long id) {
        return reservaProfesorRepo.findById(id);
    }

    public List<ReservaProfesor> findAllByProfesor(Profesor profesor) {
        return reservaProfesorRepo.findAllByProfesor(profesor);
    }

   
    public List<ReservaProfesor> findAll() {
        return reservaProfesorRepo.findAll();
    }

    
    public <S extends ReservaProfesor> S save(S entity) {
        return reservaProfesorRepo.save(entity);
    }

  
    public void deleteById(Long id) {
        reservaProfesorRepo.deleteById(id);
    }

   
    public void deleteAll(Iterable<? extends ReservaProfesor> entities) {
        reservaProfesorRepo.deleteAll(entities);
    }

   
    public Optional<ReservaProfesor> findByFechaReservaAndProfesorAndAula(LocalDate fechaReserva, Profesor profesor,
            Aula aula) {
        return reservaProfesorRepo.findByFechaReservaAndProfesorAndAula(fechaReserva, profesor, aula);
    }

    public List<ReservaProfesor> findAllByProfesorAndFechaReserva(Profesor profesor, LocalDate fechaReserva) {
        return reservaProfesorRepo.findAllByProfesorAndFechaReserva(profesor, fechaReserva);
    }
}
