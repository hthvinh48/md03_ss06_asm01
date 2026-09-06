package com.example.asm01.service;

import com.example.asm01.dto.TicketRequest;
import com.example.asm01.dto.TicketResponse;
import com.example.asm01.dto.TicketSummaryResponse;

import java.util.List;

public interface TicketService {

    TicketResponse checkIn(TicketRequest req);

    TicketResponse checkOut(Long vehicleId);

    List<TicketSummaryResponse> getTodayTicketSummary();
}