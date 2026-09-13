package com.donii.MedicamentsScheduler.services;

import com.donii.MedicamentsScheduler.dto.CreateMedicineRequest;
import com.donii.MedicamentsScheduler.dto.DeleteMedicineResponse;
import com.donii.MedicamentsScheduler.dto.MedicineResponse;
import com.donii.MedicamentsScheduler.entity.Medicine;
import com.donii.MedicamentsScheduler.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicineService {
    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicineResponse> getAllMedicines() {
        return medicineRepository.findAll()
                .stream()
                .map(m -> new MedicineResponse(
                        m.getMedicineId(),
                        m.getMedicineName(),
                        m.getMedicineDescription(),
                        m.getDailyUsageFrequency(),
                        m.getTakenCount(),
                        m.getCreatedAt()
                )).toList();
    }

    @Transactional
    public MedicineResponse createMedicine(CreateMedicineRequest req) {
        Medicine medicine = new Medicine(
                req.medicineName(),
                req.medicineDescription(),
                req.dailyUsageFrequency()
        );

        Medicine savedMedicine = medicineRepository.save(medicine);

        return new MedicineResponse(
                savedMedicine.getMedicineId(),
                savedMedicine.getMedicineName(),
                savedMedicine.getMedicineDescription(),
                savedMedicine.getDailyUsageFrequency(),
                savedMedicine.getTakenCount(),
                savedMedicine.getCreatedAt()
        );
    }

    @Transactional
    public MedicineResponse takeMedicine(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found: " + medicineId));

        medicine.setTakenCount(medicine.getTakenCount() + 1);
        Medicine savedMedicine = medicineRepository.save(medicine);
        return new MedicineResponse(
                savedMedicine.getMedicineId(),
                savedMedicine.getMedicineName(),
                savedMedicine.getMedicineDescription(),
                savedMedicine.getDailyUsageFrequency(),
                savedMedicine.getTakenCount(),
                savedMedicine.getCreatedAt()
        );
    }

    @Transactional
    public DeleteMedicineResponse deleteMedicine(Long medicineId) {
        Medicine medicineToDelete = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found: " + medicineId));

        boolean status = true;
        medicineRepository.delete(medicineToDelete);
        if (medicineRepository.existsById(medicineToDelete.getMedicineId())) {
            status = false;
            return new DeleteMedicineResponse("error", status);
        }

        return new DeleteMedicineResponse("success", status);
    }
}
