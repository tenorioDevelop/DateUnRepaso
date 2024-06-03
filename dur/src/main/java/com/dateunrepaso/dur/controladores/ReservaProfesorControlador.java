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

        boolean correcto = true;

        Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");

        // Reatachar el profesor al contexto de persistencia
        profesor = profesorImp.findById(profesor.getId()).get();

        Aula aula = aulaRepo.findById(idAula).get();

        List<ReservaProfesor> reservas = reservaProfImp.findAll();

        if (fecha.isBefore(LocalDate.now())) {
            atributos.addFlashAttribute("Error", "No puedes reservar en una fecha anterior a la fecha actual");
            correcto = false;
        } else if (horaI == horaF) {
            atributos.addFlashAttribute("Error", "No puedes reservar con la misma hora en los dos tramos de horario");
            correcto = false;
        } else if (horaI > horaF) {
            atributos.addFlashAttribute("Error", "La hora final no puede ser antes que la hora inicio");
            correcto = false;
        }

        for (ReservaProfesor reserva : reservas) {

            if (reserva.getProfesor().equals(profesor) && reserva.getFechaReserva().equals(fecha)
                    && reserva.getHoraInicio() == horaI && reserva.getHoraFin() == horaF) {
                atributos.addFlashAttribute("Error", "Ya tienes una reserva en ese horario");
                correcto = false;
            } else if (reserva.getAula().equals(aula) && reserva.getFechaReserva().equals(fecha)
                    && (horaI >= reserva.getHoraInicio() && horaF <= reserva.getHoraFin())) {
                atributos.addFlashAttribute("Error", "Ya existe una reserva en ese horario");
                correcto = false;
            } else if ((horaI > reserva.getHoraInicio() && horaI < reserva.getHoraFin())
                    || (horaF > reserva.getHoraInicio() && horaF < reserva.getHoraFin())
                            && reserva.getAula().equals(aula) && reserva.getFechaReserva().equals(fecha)) {
                atributos.addFlashAttribute("Error", "No puedes elegir una hora que ya esta ocupada por otra reserva");
                correcto = false;
            }
        }

        if (correcto) {
            ReservaProfesor reserva = new ReservaProfesor(null, profesor, aula, fecha, horaI, horaF);
            reservaProfImp.save(reserva);
            return "redirect:/app";
        } else {
            return "redirect:/reserva-profesor";
        }
    }
}
