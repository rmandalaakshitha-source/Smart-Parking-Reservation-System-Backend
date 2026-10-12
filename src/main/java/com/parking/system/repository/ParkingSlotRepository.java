package com.parking.system.repository;

import com.parking.system.model.ParkingSlot;
import com.parking.system.model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByStatus(SlotStatus status);
    List<ParkingSlot> findByFloor(String floor);
    List<ParkingSlot> findByFacilityName(String facilityName);
    List<ParkingSlot> findByCity(String city);
    Optional<ParkingSlot> findBySlotNumber(String slotNumber);
    boolean existsBySlotNumber(String slotNumber);
    long countByStatus(SlotStatus status);
}

