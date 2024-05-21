package com.dateunrepaso.dur.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class CalendarioControlador {

    @Autowired
    ReservaAlumnoImp reservaAlumnoImp;

    @GetMapping("/reservas-alumno")
    public List<ReservaAlumno> getMethodName(@RequestParam Long idAlumno) {
        return reservaAlumnoImp.getReservasAlumno(idAlumno); //TODO terminar de hacer correctamente
    }
    

}
