package com.hotel.sicig.security.service;

import com.hotel.sicig.dto.HabitacionRequestDTO;
import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public List<Habitacion> listarTodas() {
        return habitacionRepository.findAll();
    }

    @Transactional
    public Habitacion guardarHabitacion(HabitacionRequestDTO dto) {
        // Validar duplicados usando el campo 'numero' de tu entidad
        if (habitacionRepository.existsByNumero(dto.getNumeroHabitacion())) {
            throw new RuntimeException("El número de habitación " + dto.getNumeroHabitacion() + " ya se encuentra registrado.");
        }

        Habitacion habitacion = new Habitacion();
        // Mapeo exacto usando los nombres de tu Habitacion.java
        habitacion.setNumero(dto.getNumeroHabitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setEstado(dto.getEstado());
        habitacion.setPrecio(dto.getPrecioPorNoche());
        habitacion.setDescripcion(dto.getDescripcion());

        return habitacionRepository.save(habitacion);
    }
}