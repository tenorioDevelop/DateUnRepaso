package com.dateunrepaso.dur.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.ReservaProfesorRepo;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
@CrossOrigin
@PreAuthorize("permitAll()")
public class ApiControlador {

    @Autowired
    AlumnoImp alumnoImp;

    @Autowired
    ProfesorImp profesorImp;

    @Autowired
    ReservaAlumnoImp reservaAlumnoImp;

    @Autowired
    ReservaProfesorImp reservaProfesorImp;

    @SuppressWarnings("unchecked")
    @GetMapping("/reservas-alumno/{idAlumno}")
    public List<HashMap> getReservasAlumnoAPI(@PathVariable Long idAlumno) {
        List<HashMap> resultado = new ArrayList<>();
        List<ReservaAlumno> original = new ArrayList<>(
                reservaAlumnoImp.getReservasAlumno(alumnoImp.findById(idAlumno).get()));

        for (ReservaAlumno reserva : original) {
            HashMap map = new HashMap<>();
            map.put("title", formatearNombreReservaAlumno(reserva.getProfesor(), reserva.getAula()));
            map.put("start", reserva.getFechaReserva().toString() + "T" + reserva.getHoraInicio() + ":00");
            map.put("end", reserva.getFechaReserva().toString() + "T" + reserva.getHoraFin() + ":00");
            resultado.add(map);
        }

        return resultado;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/reservas-profesor/{idProfesor}")
    public List<HashMap> getReservasProfesorAPI(@PathVariable Long idProfesor,
            @RequestParam(required = false) String todos) {
        List<HashMap> resultado = new ArrayList<>();

        List<ReservaProfesor> original;

        if (todos != null) {
            if (todos.equals("s")) {
                original = reservaProfesorImp.findAll();
            } else {
                original = new ArrayList<>(
                        reservaProfesorImp.findAllByProfesor(profesorImp.findById(idProfesor).get()));
            }
        } else {
            original = new ArrayList<>(
                    reservaProfesorImp.findAllByProfesor(profesorImp.findById(idProfesor).get()));
        }

        for (ReservaProfesor reserva : original) {
            HashMap map = new HashMap<>();
            if (todos != null && todos.equals("s") && reserva.getProfesor().getId() != idProfesor) {
                map.put("title", formatearNombreReservaProfesor(reserva.getAula()) + " del profesor "
                        + reserva.getProfesor().getNomCompleto());
                map.put("className", "claseExterna");
            } else {
                map.put("title", formatearNombreReservaProfesor(reserva.getAula()));
            }
            map.put("start", reserva.getFechaReserva().toString() + "T" + reserva.getHoraInicio() + ":00");
            map.put("end", reserva.getFechaReserva().toString() + "T" + reserva.getHoraFin() + ":00");
            resultado.add(map);

        }

        return resultado;

    }

    public String formatearNombreReservaAlumno(Profesor profesor, Aula aula) {
        return profesor.getAsignatura().getNombre() + " con " + profesor.getNomCompleto() + " en el "
                + aula.getNombre();
    }

    public String formatearNombreReservaProfesor(Aula aula) {
        return "Clases en el " + aula.getNombre();
    }

}
