package com.dateunrepaso.dur.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
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
    public String getReservaAlumno(HttpSession sesion, Model model) {
        crearModel(model, sesion);
        Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
        List<ReservaProfesor> reservaP = reservaProfeImp.getReservasDeProfesorActuales();
        reservaP.removeIf(p -> !p.getLstReservaAlumno().stream().allMatch(a -> a.getId() == alumno.getId()));
        model.addAttribute("listaReservasP", reservaP);
        return "ReservaAlumno";
    }

    @Transactional
    @PostMapping("/reserva-alumno")
    public String postReservaAlumno(
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
                reservaP.getFechaReserva(), reservaP.getHoraInicio(), reservaP.getHoraFin(), reservaP);

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

        if (!reservaAlumnoImp.findAll().isEmpty()) {
            List<ReservaAlumno> reservasAlumnos = reservaAlumnoImp.findAll();
            for (ReservaAlumno reserva : reservasAlumnos) {
                if (reservaP.getFechaReserva().equals(reserva.getFechaReserva())
                        && (reservaP.getHoraInicio() >= reserva.getHoraInicio()
                        && reservaP.getHoraFin() <= reserva.getHoraFin())) {
                    atributos.addFlashAttribute("Error", "Ya tienes una reserva en esa hora y fecha");
                    esValido = false;
                }
            }
        }

        if (esValido) {
            reservaAlumnoImp.save(reservaA);
            return "redirect:/clases";
        } else {
            return "redirect:/reserva-alumno";
        }
    }

    public void crearModel(Model model, HttpSession sesion) {
        if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
            Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", alumno);
            model.addAttribute("tipoUsuario", "alumno");
        } else {
            Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", profesor);
            model.addAttribute("tipoUsuario", "profesor");
        }
    }
}
