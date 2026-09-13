package com.donii.MedicamentsScheduler.repository;

import com.donii.MedicamentsScheduler.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
