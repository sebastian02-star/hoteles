package com.hotel.sicig.controller;

import com.hotel.sicig.dto.ReservaRequestDTO;
import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.repository.ReservaRepository;
import com.hotel.sicig.security.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaService reservaService, ReservaRepository reservaRepository) {
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> registrarReserva(@RequestBody ReservaRequestDTO dto) {
        try {
            Reserva reserva = reservaService.crearReserva(dto);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar reserva: " + e.getMessage());
        }
    }
}