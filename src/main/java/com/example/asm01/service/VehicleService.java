package com.example.asm01.service;

import com.example.asm01.dto.PageResponse;
import com.example.asm01.dto.VehicleCreateRequest;
import com.example.asm01.dto.VehicleResponse;

public interface VehicleService {

    VehicleResponse createVehicle(VehicleCreateRequest request);

    PageResponse<VehicleResponse> getPagedVehicles(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword
    );
}