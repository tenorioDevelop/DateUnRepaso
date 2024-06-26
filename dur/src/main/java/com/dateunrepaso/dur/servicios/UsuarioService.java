package com.dateunrepaso.dur.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private AlumnoImp alumnoImp;

    @Autowired
    private ProfesorImp profesorImp;

    @Autowired
    private AdminImp adminImp;

    public List<Usuario> findAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(alumnoImp.findAll());
        usuarios.addAll(profesorImp.findAll());
        usuarios.addAll(adminImp.findAll());
        return usuarios;
    }

    public Optional<Usuario> findByUsername(String nomUsuario) {
        List<Usuario> usuarios = findAllUsuarios();
        return usuarios.stream().filter(u -> u.getNomUsuario().equals(nomUsuario)).findFirst();
    }
}
