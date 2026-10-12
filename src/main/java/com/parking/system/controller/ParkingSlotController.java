
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
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    // Get all parking slots
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        List<ParkingSlot> slots = parkingSlotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }

    // Get a parking slot by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(
            @PathVariable Long id) {

        ParkingSlot slot = parkingSlotService.getSlotById(id);
        return ResponseEntity.ok(slot);
    }

    // Add a new parking slot
    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(
            @Valid @RequestBody ParkingSlot parkingSlot) {

        ParkingSlot createdSlot = parkingSlotService.createSlot(parkingSlot);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdSlot);
    }

    // Update an existing parking slot
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(
            @PathVariable Long id,
            @Valid @RequestBody ParkingSlot parkingSlot) {

        ParkingSlot updatedSlot = parkingSlotService.updateSlot(id, parkingSlot);

        return ResponseEntity.ok(updatedSlot);
    }

    // Update a parking slot's status
    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSlot> updateSlotStatus(
            @PathVariable Long id,
            @RequestParam SlotStatus status) {

        ParkingSlot updatedSlot = parkingSlotService.updateSlotStatus(id, status);

        return ResponseEntity.ok(updatedSlot);
    }

    // Delete a parking slot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(
            @PathVariable Long id) {

        parkingSlotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}
