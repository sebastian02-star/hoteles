package com.hotel.sicig.repository;

import com.hotel.sicig.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;



public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    boolean existsByNumero(String numero);
}