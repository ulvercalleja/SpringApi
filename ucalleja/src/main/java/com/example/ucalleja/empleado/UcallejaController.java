package com.example.ucalleja.empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/Ucalleja")
@RequiredArgsConstructor

public class UcallejaController {

    @Autowired
    private final UcallejaService personaService;
    
    @PostMapping("/crear")
    
    public void insertarPersona(@RequestBody Ucalleja person){
        personaService.crearUcalleja(person);
    }
}
