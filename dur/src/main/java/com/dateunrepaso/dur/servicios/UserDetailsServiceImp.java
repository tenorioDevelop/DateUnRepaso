package com.dateunrepaso.dur.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Usuario;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        authoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().name())));

        return new User(usuario.getNomUsuario(),
                        usuario.getContrasena(),
                        true,
                        true,
                        true,
                        true,
                        authoritiesList);
    }
    
}
