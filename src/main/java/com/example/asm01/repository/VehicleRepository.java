package com.example.asm01.repository;

import com.example.asm01.dto.VehicleResponse;
import com.example.asm01.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("""
            SELECT new com.example.asm01.dto.VehicleResponse(
                v.id,
                v.licensePlate,
                v.color,
                v.vehicleType
            )
            FROM Vehicle v
            WHERE :keyword IS NULL
               OR LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<VehicleResponse> findAllByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}