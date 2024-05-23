package com.dateunrepaso.dur.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class CalendarioControlador {

    @Autowired
    ReservaAlumnoImp reservaAlumnoImp;

    @SuppressWarnings("unchecked")
    @GetMapping("/reservas-alumno")
    public List<HashMap> getMethodName(@RequestParam Long idAlumno) {
        List<HashMap> resultado = new ArrayList<>();
        List<ReservaAlumno> original = new ArrayList<>(reservaAlumnoImp.getReservasAlumno(idAlumno));

        for(ReservaAlumno reserva : original){
            HashMap map = new HashMap<>();
            map.put("tittle", formatearNombreReserva(reserva.getProfesor(),reserva.getAula()));
            map.put("start", reserva.getFechaReserva().toString()+"T"+reserva.getHoraInicio()+":00");
            map.put("end", reserva.getFechaReserva().toString()+"T"+reserva.getHoraFin()+":00");
            resultado.add(map);
        }

        return resultado; 
    }

    public String formatearNombreReserva(Profesor profesor, Aula aula){
        return profesor.getAsignatura().getNombre() + " con " + profesor.getNomCompleto() + " en el aula " + aula.getNombre();
    }
    

}
