package com.example.ucalleja.persona;

import java.util.List;
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

    public Optional<Ucalleja> editarUcalleja(Integer id, Ucalleja person) {
        Optional<Ucalleja> p = ucallejaRepository.findById(id);
        if (p.isPresent()) {
            if (person.getNombre() != null && !person.getNombre().isEmpty()) {
                p.get().setNombre(person.getNombre());
            }

            if (person.getApellidos() != null && !person.getApellidos().isEmpty()) {
                p.get().setApellidos(person.getApellidos());
            }

            if (person.getEmail() != null && (!person.getEmail().isEmpty())) {
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
    
    public List<Ucalleja> obtenerTodo() {
        return ucallejaRepository.findAll();
    }

    public Optional<Ucalleja> aumentarSaldo(Float saldo, Integer id){
        Optional<Ucalleja> p = ucallejaRepository.findById(id);
        if (saldo < 0) {
            saldo = saldo * -1;
        }
        if (p.isPresent()) {

            if (p.get().getSaldo() == null) {
                p.get().setSaldo(0f);
                p.get().setSaldo(saldo);
            } else {
                p.get().setSaldo(p.get().getSaldo() + saldo);
            }

            ucallejaRepository.save(p.get());
            return p;
        } else {
            return Optional.empty();
        }
        
    }

    
    public Optional<Ucalleja> reducirSaldo(Float saldo, Integer id){
        Optional<Ucalleja> p = ucallejaRepository.findById(id);
        if (saldo < 0) {
            saldo = saldo * -1;
        }

        if (p.isPresent()) {
            if (p.get().getSaldo() == null) {
                p.get().setSaldo(0f);
                p.get().setSaldo(p.get().getSaldo() - saldo);
            } else {
                p.get().setSaldo(p.get().getSaldo() - saldo);
            }

            ucallejaRepository.save(p.get());
            return p;
        } else {
            return Optional.empty();
        }
    }

    public boolean existeSaldo(String id){
        Optional<Ucalleja> p = ucallejaRepository.findById(Integer.parseInt(id));
        if (p.get().getSaldo() == null || p.get().getSaldo() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public float mediaSaldos() {
        List<Ucalleja> empleados = ucallejaRepository.findAll();
        if (empleados.isEmpty()) {
            return 0; // Si no hay empleados, el salario medio es cero
        } else {
            float totalSalarios = 0;
            for (Ucalleja empleado : empleados) {
                totalSalarios += empleado.getSaldo();
            }
            return totalSalarios / empleados.size();
        }
    }
}
