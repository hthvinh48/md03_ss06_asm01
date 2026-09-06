package com.example.asm01.service;

import com.example.asm01.dto.TicketRequest;
import com.example.asm01.dto.TicketResponse;

public interface ParkingService {

    TicketResponse checkIn(TicketRequest req);

    TicketResponse checkOut(Long vehicleId);
}