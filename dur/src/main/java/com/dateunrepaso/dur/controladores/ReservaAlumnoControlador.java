package com.dateunrepaso.dur.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservaAlumnoControlador {

    @Autowired
    ReservaAlumnoImp reservaAlumnoImp;

    @Autowired
    ReservaProfesorImp reservaProfeImp;

    @Autowired
    AlumnoImp alumnoImp; // Añadir el repositorio de Alumno

    @GetMapping("/reserva-alumno")
    public String getMain(Model model, HttpSession sesion) {
        Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
        List<ReservaProfesor> reservaP = reservaProfeImp.getReservasDeProfesorActuales();
        List<ReservaAlumno> reservaA = reservaAlumnoImp.getReservasAlumno(alumno);

        List<ReservaProfesor> mostrarReservas = new ArrayList<>();
        mostrarReservas = reservaP;

        boolean existe = false;

        for (ReservaProfesor reservaProfesor : reservaP) {
            for (ReservaAlumno reservaAlumno : reservaA) {
                if (reservaAlumno.getHoraInicio() == reservaProfesor.getHoraInicio()
                        && reservaAlumno.getHoraFin() == reservaProfesor.getHoraFin()
                        && reservaAlumno.getFechaReserva().equals(reservaProfesor.getFechaReserva())
                        && reservaAlumno.getAula().getId() == reservaProfesor.getAula().getId()
                        && reservaAlumno.getProfesor().getId() == reservaProfesor.getProfesor().getId()) {
                    existe = true;
                    mostrarReservas.remove(reservaProfesor);
                }

            }
        }

        // System.out.println("\n" + existe + "\n");
        if (existe == true) {
            model.addAttribute("listaReservasP", mostrarReservas);
        } else {
            model.addAttribute("listaReservasP", reservaP);
        }

        return "ReservaAlumno";
    }

    @Transactional
    @PostMapping("/reserva-alumno")
    public String postReservar(
            HttpSession sesion,
            RedirectAttributes atributos,
            @RequestParam(name = "idReserva") Long idReserva) {

        boolean esValido = true;

        Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
        // Reatachar el alumno al contexto de persistencia
        alumno = alumnoImp.findById(alumno.getId())
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));

        ReservaProfesor reservaP = reservaProfeImp.findById(idReserva).get();

        ReservaAlumno reservaA = new ReservaAlumno(null, alumno, reservaP.getProfesor(), reservaP.getAula(),
                reservaP.getFechaReserva(), reservaP.getHoraInicio(), reservaP.getHoraFin());

        // Busca si la reserva que se quiere crear ya existe
        if (reservaAlumnoImp.findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(reservaP.getAula(),
                reservaP.getProfesor(), alumno, reservaP.getFechaReserva(), reservaP.getHoraInicio()).isPresent()) {
            atributos.addFlashAttribute("Error", "Ya existe una reserva suya para ese dia a esa hora");
            esValido = false;
        } else if (reservaAlumnoImp.findByAulaAndFechaReservaAndProfesor(reservaP.getAula(), reservaP.getFechaReserva(),
                reservaP.getProfesor()).get().size() >= reservaP.getAula().getCantidadMaxAlumnos()) {
            atributos.addFlashAttribute("Error", "Se ha superado el numero de alumnos en este aula");
            esValido = false;
        }

        if (esValido) {
            reservaAlumnoImp.save(reservaA);
            return "redirect:/clases";
        } else {
            return "redirect:/reserva-alumno";
        }
    }
}
