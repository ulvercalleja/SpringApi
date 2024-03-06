package com.example.ucalleja.persona;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Ucalleja")
@RequiredArgsConstructor

public class UcallejaController {

    @Autowired
    private final UcallejaService personaService;

    // Endpoint para crear una nueva persona
    @PostMapping("/crearElemento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se ha creado la persona correctamente.", content = {
                    @Content(schema = @Schema(implementation = Ucalleja.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "206", description = "Elemento creado con información parcial")
    })

    public ResponseEntity<String> crearElemento(@RequestBody Ucalleja person) {
        // Lógica para crear una nueva persona
        personaService.crearUcalleja(person);
        // Validación de que la información proporcionada sea completa
        if (person.getNombre() == null || person.getApellidos() == null || person.getEmail() == null
                || (person.getSaldo()) == null ||
                person.getNombre().isEmpty() || person.getApellidos().isEmpty() || person.getEmail().isEmpty()) {
            return ResponseEntity.status(206).body("Elemento creado con información parcial");
        } else
            return ResponseEntity.ok("Se ha creado la persona correctamente");
    }

    // Endpoint para editar una persona existente
    @PostMapping("editarElemento/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se ha actualizado la persona correctamente."),
            @ApiResponse(responseCode = "201", description = "Elemento actualizado parcialmente con éxito."),
            @ApiResponse(responseCode = "209", description = "Elemento no encontrado.")
    })

    public ResponseEntity<String> editarElemento(@PathVariable String id, @RequestBody Ucalleja person) {
        // Lógica para editar una persona existente
        if (personaService.editarUcalleja(Integer.parseInt(id), person).isPresent()) {
            // Validación de que la información proporcionada sea completa
            if (person.getNombre() == null || person.getApellidos() == null || person.getEmail() == null
                    || (person.getSaldo()) == null ||
                    person.getNombre().isEmpty() || person.getApellidos().isEmpty() || person.getEmail().isEmpty()) {
                return ResponseEntity.status(201).body("Elemento actualizado parcialmente con éxito.");
            } else {
                personaService.editarUcalleja(Integer.parseInt(id), person);
                return ResponseEntity.status(200).body("Se ha actualizado la persona correctamente.");
            }
        } else {
            return ResponseEntity.status(209).body("Elemento no encontrado.");
        }
    }

    @GetMapping("buscarElemento/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se encontrado."),
            @ApiResponse(responseCode = "209", description = "Elemento no encontrado.")
    })

    public ResponseEntity<Object> buscarElemento(@PathVariable String id) {
        Optional<Ucalleja> personaEncontrada = personaService.buscarUcalleja(Integer.parseInt(id)); // Lógica para buscar la persona con el ID proporcionado
        if (personaEncontrada.isPresent()) {
            return ResponseEntity.status(200).body(personaEncontrada);
        } else {
            return ResponseEntity.status(209).body("Elemento no encontrado.");
        }
    }

    @DeleteMapping("borrarElemento/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Elemento eliminado."),
        @ApiResponse(responseCode = "209", description = "Elemento no eliminado.")
    })

    public ResponseEntity<String> borrarElemento(@PathVariable String id) {
        if (personaService.borrarUcalleja(Integer.parseInt(id))) {
            return ResponseEntity.status(200).body("Elemento eliminado.");
        } else {
            return ResponseEntity.status(209).body("Elemento no eliminado.");
        }

    }

    @DeleteMapping("borrarTodo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Elementos eliminados."),
        @ApiResponse(responseCode = "209", description = "Elementos no eliminados.")
    })
    
    public ResponseEntity<String> borrarTodo() {
        if (personaService.borrarTodo()) {
            return ResponseEntity.status(200).body("Elementos eliminados.");
        } else {
            return ResponseEntity.status(209).body("Elementos no eliminados.");
        }
    }

    @GetMapping("obtenerTodo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Todos los elementos son mostrados correctamente."),
        @ApiResponse(responseCode = "209", description = "No hay ningún elemento para mostrar.")
    })
    
    public ResponseEntity<Object> obtenerTodo() {
        List<Ucalleja> ulvers = personaService.obtenerTodo();
        if (!ulvers.isEmpty()) {
            return ResponseEntity.status(200).body(ulvers);
        } else {
            return ResponseEntity.status(209).body("No hay ningún elemento para mostrar.");
        }
    }

    @PostMapping("aumentarSaldo/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saldo aumentado correctamente."),
        @ApiResponse(responseCode = "201", description = "Saldo inicializado y aumentado con éxito."),
        @ApiResponse(responseCode = "209", description = "No existe ese ID.")
    })

    public ResponseEntity<String> aumentarSaldo(@RequestBody Float cantidad, @PathVariable String id) {
        Optional<Ucalleja> personaEncontrada = personaService.buscarUcalleja(Integer.parseInt(id)); // Lógica para buscar la persona con el ID proporcionado
        if (personaEncontrada.isPresent()) {
            if (personaService.existeSaldo(id)) {
                personaService.aumentarSaldo(cantidad, Integer.parseInt(id));
                return ResponseEntity.status(200).body("Saldo aumentado correctamente.");
            } else {
                personaService.aumentarSaldo(cantidad, Integer.parseInt(id));
                return ResponseEntity.status(201).body("Saldo inicializado y aumentado con éxito..");
            }
        } else {
            return ResponseEntity.status(209).body("No existe ese ID.");
        }
    }

    @PostMapping("reducirSaldo/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saldo devengado correctamente y positivo."),
        @ApiResponse(responseCode = "201", description = "Saldo devengado y negativo."),
        @ApiResponse(responseCode = "209", description = "No existe ese ID.")
    })

    public ResponseEntity<String> reducirSaldo(@RequestBody Float cantidad, @PathVariable String id) {
        Optional<Ucalleja> personaEncontrada = personaService.buscarUcalleja(Integer.parseInt(id)); // Lógica para buscar la persona con el ID proporcionado
        if (personaEncontrada.isPresent()) {
            if (personaService.existeSaldo(id)) {
                personaService.reducirSaldo(cantidad, Integer.parseInt(id));
                return ResponseEntity.status(200).body("Saldo devengado correctamente y reducido.");
            } else {
                personaService.reducirSaldo(cantidad, Integer.parseInt(id));
                return ResponseEntity.status(201).body("Saldo devengado y negativo.");
            }
        } else {
            return ResponseEntity.status(209).body("No existe ese ID.");
        }
    }

    @GetMapping("mediaSaldos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saldo medio de todos los registros."),
        @ApiResponse(responseCode = "201", description = "El saldo medio es negativo.")
    })
    public ResponseEntity<Object> mediaSaldos() {
        Float salarioMedio = personaService.mediaSaldos();
        if (salarioMedio > 0){
            return ResponseEntity.status(200).body("Saldo medio de todos los registros. \n" + salarioMedio);
        } else {
            return ResponseEntity.status(201).body("El saldo medio es negativo. \n" + salarioMedio);
        }
    }
    
}