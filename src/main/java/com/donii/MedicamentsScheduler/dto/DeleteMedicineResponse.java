package com.donii.MedicamentsScheduler.dto;

public record DeleteMedicineResponse(
        String statusMessage,
        Boolean deleted
) {
}
