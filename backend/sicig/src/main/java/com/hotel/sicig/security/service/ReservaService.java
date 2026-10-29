package com.hotel.sicig.security.service;

import com.hotel.sicig.dto.ReservaRequestDTO;
import com.hotel.sicig.model.Cliente;
import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.repository.ClienteRepository;
import com.hotel.sicig.repository.HabitacionRepository;
import com.hotel.sicig.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {

    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final ReservaRepository reservaRepository;

    public ReservaService(ClienteRepository clienteRepository,
                          HabitacionRepository habitacionRepository,
                          ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva crearReserva(ReservaRequestDTO dto) {
        
        Cliente cliente = clienteRepository.findByNumeroDocumento(dto.getNumeroDocumento())
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombre(dto.getNombre());
                    nuevo.setApellido(dto.getApellido());
                    nuevo.setNacionalidad(dto.getNacionalidad());
                    nuevo.setTipoDocumento(dto.getTipoDocumento());
                    nuevo.setNumeroDocumento(dto.getNumeroDocumento());
                    return clienteRepository.save(nuevo);
                });

        Habitacion habitacion = habitacionRepository.findById(dto.getHabitacionId())
                .orElseThrow(() -> new RuntimeException("La habitación no existe"));


        if ("Ocupada".equalsIgnoreCase(habitacion.getEstado()) || "Reservada".equalsIgnoreCase(habitacion.getEstado())) {
            throw new RuntimeException("La habitación seleccionada ya se encuentra ocupada o reservada.");
        }

        
        List<Reserva> reservasExistentes = reservaRepository.buscarReservasSolapadas(
            dto.getHabitacionId(), 
            dto.getFechaIngreso(), 
            dto.getFechaSalida()
        );

        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException(" La habitación " + habitacion.getNumero() + " ya cuenta con una reserva activa en esas fechas.");
        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        
        reserva.setCantidadPersonas(dto.getCantidadPersonas() != null ? dto.getCantidadPersonas() : 1);
        
        if (dto.getCostoTotal() != null) {
            reserva.setCostoTotal(dto.getCostoTotal());
        } else {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(dto.getFechaIngreso(), dto.getFechaSalida());
            dias = dias <= 0 ? 1 : dias;
            double totalCalculado = habitacion.getPrecio() * reserva.getCantidadPersonas() * dias;
            reserva.setCostoTotal(totalCalculado);
        }

        reserva.setEstado("Reservada");

        Reserva reservaGuardada = reservaRepository.save(reserva);

        habitacion.setEstado("Reservada");
        habitacionRepository.save(habitacion);

        return reservaGuardada;
    }
}