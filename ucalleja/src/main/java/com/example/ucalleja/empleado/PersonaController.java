package com.example.ucalleja.empleado;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/persona")
@RequiredArgsConstructor

public class PersonaController {

    private final PersonaService personaService;
    
    @PostMapping("/crear")
    
    public void insertarPersona(@RequestBody Persona person){
        personaService.crearPersona(person);
    }
}
