package com.example.ucalleja.empleado;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PersonaService {

    private PersonaRepository personaRepository;

    public boolean borrarTodo() {
        personaRepository.deleteAll();
        return true;
    }
}
