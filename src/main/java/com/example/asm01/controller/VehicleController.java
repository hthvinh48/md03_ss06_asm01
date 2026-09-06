package com.example.asm01.controller;

import com.example.asm01.dto.PageResponse;
import com.example.asm01.dto.VehicleCreateRequest;
import com.example.asm01.dto.VehicleResponse;
import com.example.asm01.response.ApiResponse;
import com.example.asm01.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(
            @RequestBody VehicleCreateRequest request
    ) {
        VehicleResponse vehicle = vehicleService.createVehicle(request);

        ApiResponse<VehicleResponse> response =
                new ApiResponse<>(
                        true,
                        "Create vehicle successfully",
                        vehicle
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String keyword
    ) {
        PageResponse<VehicleResponse> vehicles =
                vehicleService.getPagedVehicles(
                        page,
                        size,
                        sortBy,
                        direction,
                        keyword
                );

        ApiResponse<PageResponse<VehicleResponse>> response =
                new ApiResponse<>(
                        true,
                        "Get vehicles successfully",
                        vehicles
                );

        return ResponseEntity.ok(response);
    }
}