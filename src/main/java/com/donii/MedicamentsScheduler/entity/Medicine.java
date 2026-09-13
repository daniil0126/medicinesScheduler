package com.donii.MedicamentsScheduler.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long medicineId;

    @Column(name = "medicine_name", nullable = false)
    String medicineName;

    @Column(name = "medicine_description")
    String medicineDescription;

    @Column(name = "daily_usage_frequency", nullable = false)
    int dailyUsageFrequency;

    @Column(name = "taken_count")
    int takenCount;

    @Column(name = "created_at")
    LocalDate createdAt;

    // empty constructor for JPA generation
    public Medicine() {}

    public Medicine(String medicineName,  String medicineDescription, int dailyUsageFrequency) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
        this.dailyUsageFrequency = dailyUsageFrequency;
    }

    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) {  this.medicineId = medicineId; }

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public String getMedicineDescription() { return medicineDescription; }
    public void setMedicineDescription(String medicineDescription) { this.medicineDescription = medicineDescription; }

    public int getDailyUsageFrequency() { return dailyUsageFrequency; }
    public void setDailyUsageFrequency(int dailyUsageFrequency) { this.dailyUsageFrequency = dailyUsageFrequency; }

    public int getTakenCount() { return takenCount; }
    public void setTakenCount(int takenCount) { this.takenCount = takenCount; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

}
