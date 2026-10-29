package com.hotel.sicig.repository;

import com.hotel.sicig.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.habitacion.id = :habitacionId " +
           "AND r.estado <> 'Cancelada' " +
           "AND r.fechaIngreso < :fechaSalida AND r.fechaSalida > :fechaIngreso")
    List<Reserva> buscarReservasSolapadas(@Param("habitacionId") Long habitacionId, 
                                          @Param("fechaIngreso") LocalDate fechaIngreso, 
                                          @Param("fechaSalida") LocalDate fechaSalida);
}