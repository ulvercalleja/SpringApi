package com.example.ucalleja.empleado;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UcallejaService {
    @Autowired
    private UcallejaRepository ucallejaRepository;

    public void crearUcalleja(Ucalleja persona){
        ucallejaRepository.save(persona);
    }

    public Optional<Ucalleja> editarcrearUcalleja(Integer id, Ucalleja person) {
        Optional<Ucalleja> p = ucallejaRepository.findById(id);
        if (p.isPresent()) {
            if (person.getNombre() != null && person.getNombre().isEmpty()) {
                p.get().setNombre(person.getNombre());
            }

            if (person.getApellidos() != null && person.getApellidos().isEmpty()) {
                p.get().setApellidos(person.getApellidos());
            }

            if (person.getEmail() != null && person.getEmail().isEmpty()) {
                p.get().setEmail(person.getEmail());
            }

            ucallejaRepository.save(p.get());
        }
        return p;
    }
    
    public Optional<Ucalleja> buscarUcalleja(Integer id) {
        return ucallejaRepository.findById(id);
    }

    public boolean borrarUcalleja(Integer id) {
        if (ucallejaRepository.findById(id).isPresent()) {
            ucallejaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    public boolean borrarTodo() {
        ucallejaRepository.deleteAll();
        return true;
    }
    /* 
    public aumentarSaldo(Integer id, float saldo){

    }

    public reducirSaldo(Integer id, float saldo){
        
    }*/
}
