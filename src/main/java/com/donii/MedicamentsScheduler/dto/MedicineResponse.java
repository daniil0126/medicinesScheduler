package com.donii.MedicamentsScheduler.dto;

import java.time.LocalDate;

public record MedicineResponse(
        Long medicineId,
        String medicineName,
        String medicineDescription,
        int dailyUsageFrequency,
        int takenCount,
        LocalDate createdAt
) {
}
