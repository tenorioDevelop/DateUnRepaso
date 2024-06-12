package com.dateunrepaso.dur.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;
import com.dateunrepaso.dur.utilidades.UtilidadesString;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.enums.Roles;

@Controller
@PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
@RequestMapping("/gestion-usuario")
public class GestionUsuarioControlador {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AlumnoImp alumnoImp;

    @Autowired
    ProfesorImp profesorImp;

    @GetMapping("")
    public String getGestionUsuario(Model model) {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        model.addAttribute("usuario", usuario);

        return "GestionUsuario";
    }

    @PostMapping("/editar")
    public String postEditarGestionUsuario(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "dni") String dni,
            @RequestParam(name = "fechaNac") String fechaNac,
            @RequestParam(name = "correo") String correo,
            RedirectAttributes atributos,
            @RequestParam(name = "contrasena") String contrasena, Model model) {

        model.addAttribute("paginaActiva", "clases");
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuario", usuario);

        boolean correcto = false;

        if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))
                && !correo.equals(usuario.getCorreo())) {
            atributos.addFlashAttribute("Error", "El correo indicado ya existe");
        } else if (usuarios.stream().anyMatch(u -> u.getDni().equals(dni)) && !dni.equals(usuario.getDni())) {
            atributos.addFlashAttribute("Error", "El dni indicado ya existe");
        } else if (!UtilidadesString.esMayorEdad(fechaNac, 18)) {
            atributos.addFlashAttribute("Error", "El profesor tiene que ser mayor de 18 años");
        } else {
            correcto = true;
        }

        if (correcto) {
            if (usuario.getRol() == Roles.ALUMNO) {
                alumnoImp.actualizarAlumno(id, nombre, nombreUsuario, dni, correo, encriptarContrasenia(contrasena),
                        fechaNac);
            } else {
                profesorImp.actualizarProfesor(id, nombre, nombreUsuario, correo, encriptarContrasenia(contrasena),
                        fechaNac, dni,
                        profesorImp.findById(id).get().getAsignatura());
            }
        }

        return "redirect:/gestion-usuario";
    }

    private String encriptarContrasenia(String contra) {
        return new BCryptPasswordEncoder().encode(contra);
    }

}
