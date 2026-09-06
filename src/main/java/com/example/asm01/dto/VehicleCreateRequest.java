package com.example.asm01.dto;

import com.example.asm01.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequest {

    private String licensePlate;
    private String color;
    private VehicleType vehicleType;
}