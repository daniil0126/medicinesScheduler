package com.donii.MedicamentsScheduler.dto;

public record CreateMedicineRequest(
        String medicineName,
        String medicineDescription,
        int dailyUsageFrequency
) {
}
