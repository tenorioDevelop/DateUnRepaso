package com.dateunrepaso.dur.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/panel-admin")
public class AdminControlador {


    @GetMapping("")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    


}
