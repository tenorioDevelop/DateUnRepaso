package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaProfesorControlador {

    @Autowired
    private AulaRepo aulaRepo;

    @Autowired
    private ReservaProfesorImp reservaProfImp;

    @Autowired
    private ProfesorImp profesorImp;

    @GetMapping("/reserva-profesor")
    public String getMain(Model model, HttpSession sesion) {
        if (sesion.getAttribute("usuarioLogeado") != null) {
            model.addAttribute("usuario", sesion.getAttribute("usuarioLogeado"));

            List<Aula> aulas = new ArrayList<>();
            aulas = aulaRepo.findAll();
            model.addAttribute("listaAulas", aulas);

            return "ReservaProfesor";
        }

        return "redirect:/";
    }

    @Transactional
    @PostMapping("/reserva-profesor")
    public String postReservar(@RequestParam(name = "resProfAula") Long idAula,
            @RequestParam(name = "resProfFecha") LocalDate fecha,
            @RequestParam(name = "resProfHoraI") int horaI,
            @RequestParam(name = "resProfHoraF") int horaF,
            HttpSession sesion,
            RedirectAttributes atributos) {

        Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");

        // Reatachar el profesor al contexto de persistencia
        profesor = profesorImp.findById(profesor.getId())
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));

        Aula aula = aulaRepo.findById(idAula).orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));

        List<ReservaProfesor> reservas = reservaProfImp.findAll();

        for (ReservaProfesor reserva : reservas) {

            if (reserva.getProfesor().equals(profesor) && reserva.getFechaReserva().equals(fecha)
                    && reserva.getHoraInicio() == horaI && reserva.getHoraFin() == horaF) {
                atributos.addFlashAttribute("Error", "Ya tienes una reserva en ese horario");
                return "redirect:/reserva-profesor";
            } else if (reserva.getAula().equals(aula) && reserva.getFechaReserva().equals(fecha) && (horaI >= reserva.getHoraInicio() && horaF <= reserva.getHoraFin())) {
                atributos.addFlashAttribute("Error", "Ya existe una reserva en ese horario");
                return "redirect:/reserva-profesor";
            }
        }

        if (fecha.isBefore(LocalDate.now())) {
            atributos.addFlashAttribute("Error", "No puedes reservar en una fecha anterior a la fecha actual");
            return "redirect:/reserva-profesor";
        }
        if (horaI == horaF) {
            atributos.addFlashAttribute("Error", "No puedes reservar con la misma hora en los dos tramos de horario");
            return "redirect:/reserva-profesor";
        }

        if (horaI < horaF) {
            ReservaProfesor reserva = new ReservaProfesor(null, profesor, aula, fecha, horaI, horaF);
            reservaProfImp.save(reserva);
            return "redirect:/app";
        } else {
            atributos.addFlashAttribute("Error", "La hora final no puede ser antes que la hora inicio");
            return "redirect:/reserva-profesor";
        }
    }
}
