package com.example.asm01.controller;

import com.example.asm01.dto.TicketRequest;
import com.example.asm01.dto.TicketResponse;
import com.example.asm01.dto.TicketSummaryResponse;
import com.example.asm01.response.ApiResponse;
import com.example.asm01.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingController {

    private final TicketService ticketService;

    public ParkingController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkIn(
            @RequestBody TicketRequest req
    ) {
        TicketResponse ticket = ticketService.checkIn(req);

        ApiResponse<TicketResponse> response = new ApiResponse<>(
                true,
                "Vehicle checked in successfully",
                ticket
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/check-out/{vehicleId}")
    public ResponseEntity<ApiResponse<TicketResponse>> checkOut(
            @PathVariable Long vehicleId
    ) {
        TicketResponse ticket = ticketService.checkOut(vehicleId);

        ApiResponse<TicketResponse> response = new ApiResponse<>(
                true,
                "Vehicle checked out successfully",
                ticket
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<TicketSummaryResponse>>> getTodayTicketSummary() {

        List<TicketSummaryResponse> tickets =
                ticketService.getTodayTicketSummary();

        ApiResponse<List<TicketSummaryResponse>> response =
                new ApiResponse<>(
                        true,
                        "Get today's ticket summary successfully",
                        tickets
                );

        return ResponseEntity.ok(response);
    }
}