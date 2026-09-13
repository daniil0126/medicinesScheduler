package com.donii.MedicamentsScheduler.api;

import com.donii.MedicamentsScheduler.dto.CreateMedicineRequest;
import com.donii.MedicamentsScheduler.dto.DeleteMedicineResponse;
import com.donii.MedicamentsScheduler.dto.MedicineResponse;
import com.donii.MedicamentsScheduler.services.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping()
    public List<MedicineResponse> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @PostMapping("/create_medicine")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineResponse createMedicine(@RequestBody CreateMedicineRequest request) {
        return medicineService.createMedicine(request);
    }

    @PostMapping("/{id}/take")
    public MedicineResponse takeMedicine(@PathVariable Long id) {
        return medicineService.takeMedicine(id);
    }
    
    @DeleteMapping("/{id}")
    public DeleteMedicineResponse deleteMedicine(@PathVariable Long id) {
        return medicineService.deleteMedicine(id);
    }
}
