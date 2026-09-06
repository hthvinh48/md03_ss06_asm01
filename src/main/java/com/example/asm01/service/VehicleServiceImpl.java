package com.example.asm01.service;

import com.example.asm01.dto.PageResponse;
import com.example.asm01.dto.VehicleCreateRequest;
import com.example.asm01.dto.VehicleResponse;
import com.example.asm01.model.Vehicle;
import com.example.asm01.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = new Vehicle();

        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setColor(request.getColor());
        vehicle.setVehicleType(request.getVehicleType());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponse(
                savedVehicle.getId(),
                savedVehicle.getLicensePlate(),
                savedVehicle.getColor(),
                savedVehicle.getVehicleType()
        );
    }

    @Override
    public PageResponse<VehicleResponse> getPagedVehicles(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword
    ) {
        if (page < 0) {
            page = 0;
        }

        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isBlank()
                && direction != null && !direction.isBlank()) {

            Sort.Direction sortDirection =
                    Sort.Direction.fromString(direction);

            sort = Sort.by(sortDirection, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        Page<VehicleResponse> vehiclePage =
                vehicleRepository.findAllByKeyword(keyword, pageable);

        return new PageResponse<>(
                vehiclePage.getContent(),
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages(),
                vehiclePage.isLast()
        );
    }
}