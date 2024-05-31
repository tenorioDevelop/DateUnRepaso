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
public class ReservaProfesorImp implements ReservaProfesorRepo {

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

    @Override
    public List<ReservaProfesor> findByAulaId(Long id) {
        return reservaProfesorRepo.findByAulaId(id);
    }

    @Override
    public Optional<ReservaProfesor> findById(Long id) {
        return reservaProfesorRepo.findById(id);
    }

    @Override
    public List<ReservaProfesor> findAllByProfesor(Profesor profesor) {
        return reservaProfesorRepo.findAllByProfesor(profesor);
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flush'");
    }

    @Override
    public <S extends ReservaProfesor> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAndFlush'");
    }

    @Override
    public <S extends ReservaProfesor> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllAndFlush'");
    }

    @Override
    public void deleteAllInBatch(Iterable<ReservaProfesor> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllByIdInBatch'");
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public ReservaProfesor getOne(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public ReservaProfesor getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public ReservaProfesor getReferenceById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferenceById'");
    }

    @Override
    public <S extends ReservaProfesor> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends ReservaProfesor> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends ReservaProfesor> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public List<ReservaProfesor> findAll() {
        return reservaProfesorRepo.findAll();
    }

    @Override
    public List<ReservaProfesor> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    @Override
    public <S extends ReservaProfesor> S save(S entity) {
        return reservaProfesorRepo.save(entity);
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void deleteById(Long id) {
        reservaProfesorRepo.deleteById(id);
    }

    @Override
    public void delete(ReservaProfesor entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
    }

    @Override
    public void deleteAll(Iterable<? extends ReservaProfesor> entities) {
        reservaProfesorRepo.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public List<ReservaProfesor> findAll(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Page<ReservaProfesor> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends ReservaProfesor> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public <S extends ReservaProfesor> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends ReservaProfesor> long count(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public <S extends ReservaProfesor> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    @Override
    public <S extends ReservaProfesor, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }

    @Override
    public Optional<ReservaProfesor> findByProfesor(Profesor profesor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByProfesor'");
    }

    @Override
    public Optional<ReservaProfesor> findByFechaReservaAndProfesorAndAula(LocalDate fechaReserva, Profesor profesor,
            Aula aula) {
        return reservaProfesorRepo.findByFechaReservaAndProfesorAndAula(fechaReserva, profesor, aula);
    }

    @Override
    public List<ReservaProfesor> findAllByProfesorAndFechaReserva(Profesor profesor, LocalDate fechaReserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByProfesorAndFechaReserva'");
    }
}
