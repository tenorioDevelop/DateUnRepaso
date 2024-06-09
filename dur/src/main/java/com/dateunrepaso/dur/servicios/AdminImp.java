package com.dateunrepaso.dur.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Administrador;
import com.dateunrepaso.dur.repositorios.AdminRepo;

@Service
public class AdminImp {

    @Autowired
    AdminRepo adminRepo;

    public List<Administrador> findAll() {
        return adminRepo.findAll();
    }

}
