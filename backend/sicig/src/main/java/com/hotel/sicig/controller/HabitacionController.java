package com.hotel.sicig.controller;

import com.hotel.sicig.repository.HabitacionRepository;
import com.hotel.sicig.model.Habitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")

@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionRepository habitacionRepository;

    @GetMapping
    public ResponseEntity<List<Habitacion>> listarHabitaciones() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        return ResponseEntity.ok(habitaciones);
    }

    @PostMapping
    public ResponseEntity<?> guardarHabitacion(@RequestBody Habitacion habitacion) {
        try {
            Habitacion nuevaHabitacion = habitacionRepository.save(habitacion);
            return ResponseEntity.ok(nuevaHabitacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al registrar la habitación en la base de datos: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHabitacion(@PathVariable Long id) {
        try {
            if (!habitacionRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            habitacionRepository.deleteById(id);
            return ResponseEntity.ok().body("Habitación eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("No se puede eliminar la habitación: " + e.getMessage());
        }
    }
}