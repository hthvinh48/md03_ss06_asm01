package com.example.asm01.repository;

import com.example.asm01.dto.TicketSummaryResponse;
import com.example.asm01.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    Optional<ParkingTicket> findFirstByVehicleIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(
            Long vehicleId
    );

    @Query("""
            SELECT new com.example.asm01.dto.TicketSummaryResponse(
                pt.id,
                pt.vehicle.licensePlate,
                pt.zone.name,
                pt.checkInTime,
                pt.checkOutTime
            )
            FROM ParkingTicket pt
            WHERE pt.checkInTime >= :startOfDay
              AND pt.checkInTime < :startOfNextDay
            """)
    List<TicketSummaryResponse> findTodayTicketSummary(
            LocalDateTime startOfDay,
            LocalDateTime startOfNextDay
    );
}