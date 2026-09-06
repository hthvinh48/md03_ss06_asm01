package com.example.asm01.controller;

import com.example.asm01.dto.TicketRequest;
import com.example.asm01.dto.TicketResponse;
import com.example.asm01.response.ApiResponse;
import com.example.asm01.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkIn(
            @RequestBody TicketRequest req
    ) {
        TicketResponse ticket = parkingService.checkIn(req);

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
        TicketResponse ticket = parkingService.checkOut(vehicleId);

        ApiResponse<TicketResponse> response = new ApiResponse<>(
                true,
                "Vehicle checked out successfully",
                ticket
        );

        return ResponseEntity.ok(response);
    }
}