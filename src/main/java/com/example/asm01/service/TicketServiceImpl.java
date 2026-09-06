package com.example.asm01.service;

import com.example.asm01.dto.TicketRequest;
import com.example.asm01.dto.TicketResponse;
import com.example.asm01.dto.TicketSummaryResponse;
import com.example.asm01.model.ParkingTicket;
import com.example.asm01.model.Vehicle;
import com.example.asm01.model.Zone;
import com.example.asm01.repository.ParkingTicketRepository;
import com.example.asm01.repository.VehicleRepository;
import com.example.asm01.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final VehicleRepository vehicleRepository;
    private final ZoneRepository zoneRepository;
    private final ParkingTicketRepository parkingTicketRepository;

    public TicketServiceImpl(
            VehicleRepository vehicleRepository,
            ZoneRepository zoneRepository,
            ParkingTicketRepository parkingTicketRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.zoneRepository = zoneRepository;
        this.parkingTicketRepository = parkingTicketRepository;
    }

    @Override
    @Transactional
    public TicketResponse checkIn(TicketRequest req) {
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        if (zone.getOccupiedSpots() >= zone.getCapacity()) {
            throw new RuntimeException("Zone is full");
        }

        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setZone(zone);
        ticket.setCheckInTime(LocalDateTime.now());

        zone.setOccupiedSpots(zone.getOccupiedSpots() + 1);

        ParkingTicket savedTicket = parkingTicketRepository.save(ticket);
        zoneRepository.save(zone);

        return new TicketResponse(
                savedTicket.getId(),
                vehicle.getLicensePlate(),
                zone.getName(),
                savedTicket.getCheckInTime(),
                savedTicket.getCheckOutTime()
        );
    }

    @Override
    @Transactional
    public TicketResponse checkOut(Long vehicleId) {
        ParkingTicket ticket = parkingTicketRepository
                .findFirstByVehicleIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle is not parked"));

        ticket.setCheckOutTime(LocalDateTime.now());

        Zone zone = ticket.getZone();

        if (zone.getOccupiedSpots() > 0) {
            zone.setOccupiedSpots(zone.getOccupiedSpots() - 1);
        }

        parkingTicketRepository.save(ticket);
        zoneRepository.save(zone);

        return new TicketResponse(
                ticket.getId(),
                ticket.getVehicle().getLicensePlate(),
                zone.getName(),
                ticket.getCheckInTime(),
                ticket.getCheckOutTime()
        );
    }

    @Override
    @Transactional
    public List<TicketSummaryResponse> getTodayTicketSummary() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();

        return parkingTicketRepository.findTodayTicketSummary(
                startOfDay,
                startOfNextDay
        );
    }
}