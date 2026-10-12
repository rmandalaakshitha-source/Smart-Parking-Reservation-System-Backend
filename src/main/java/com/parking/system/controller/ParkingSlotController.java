package com.parking.system.controller;

import com.parking.system.model.ParkingSlot;
import com.parking.system.model.SlotStatus;
import com.parking.system.service.ParkingSlotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://127.0.0.1:5173", "http://127.0.0.1:5174" })
public class ParkingSlotController {

    private final ParkingSlotService slotService;

    @Autowired
    public ParkingSlotController(ParkingSlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots(
            @RequestParam(required = false) SlotStatus status,
            @RequestParam(required = false) String floor,
            @RequestParam(required = false) String facilityName,
            @RequestParam(required = false) String city) {
        if (status != null) {
            return ResponseEntity.ok(slotService.getSlotsByStatus(status));
        }
        if (floor != null && !floor.isEmpty()) {
            return ResponseEntity.ok(slotService.getSlotsByFloor(floor));
        }
        if (facilityName != null && !facilityName.isEmpty()) {
            return ResponseEntity.ok(slotService.getSlotsByFacility(facilityName));
        }
        if (city != null && !city.isEmpty()) {
            return ResponseEntity.ok(slotService.getSlotsByCity(city));
        }
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.getSlotById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(@Valid @RequestBody ParkingSlot slot) {
        ParkingSlot createdSlot = slotService.createSlot(slot);
        return new ResponseEntity<>(createdSlot, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(@PathVariable Long id, @Valid @RequestBody ParkingSlot slot) {
        ParkingSlot updatedSlot = slotService.updateSlot(id, slot);
        return ResponseEntity.ok(updatedSlot);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSlot> updateSlotStatus(@PathVariable Long id, @RequestParam SlotStatus status) {
        ParkingSlot updatedSlot = slotService.updateSlotStatus(id, status);
        return ResponseEntity.ok(updatedSlot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}

