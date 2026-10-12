package com.parking.system.service;

import com.parking.system.exception.BadRequestException;
import com.parking.system.exception.ResourceNotFoundException;
import com.parking.system.model.ParkingSlot;
import com.parking.system.model.SlotStatus;
import com.parking.system.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository slotRepository;

    @Autowired
    public ParkingSlotService(ParkingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<ParkingSlot> getAllSlots() {
        return slotRepository.findAll();
    }

    public List<ParkingSlot> getSlotsByStatus(SlotStatus status) {
        return slotRepository.findByStatus(status);
    }

    public List<ParkingSlot> getSlotsByFloor(String floor) {
        return slotRepository.findByFloor(floor);
    }

    public List<ParkingSlot> getSlotsByFacility(String facilityName) {
        return slotRepository.findByFacilityName(facilityName);
    }

    public List<ParkingSlot> getSlotsByCity(String city) {
        return slotRepository.findByCity(city);
    }

    public ParkingSlot getSlotById(Long id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking slot not found with id: " + id));
    }

    public ParkingSlot createSlot(ParkingSlot slot) {
        if (slotRepository.existsBySlotNumber(slot.getSlotNumber())) {
            throw new BadRequestException("Slot number '" + slot.getSlotNumber() + "' already exists!");
        }
        if (slot.getStatus() == null) {
            slot.setStatus(SlotStatus.AVAILABLE);
        }
        if (slot.getFacilityName() == null || slot.getFacilityName().trim().isEmpty()) {
            slot.setFacilityName("Sample Parking Facility A");
        }
        if (slot.getAddress() == null || slot.getAddress().trim().isEmpty()) {
            slot.setAddress("123 Sample Location Street");
        }
        if (slot.getCity() == null || slot.getCity().trim().isEmpty()) {
            slot.setCity("Sample City");
        }
        return slotRepository.save(slot);
    }

    public ParkingSlot updateSlot(Long id, ParkingSlot slotDetails) {
        ParkingSlot existingSlot = getSlotById(id);

        if (!existingSlot.getSlotNumber().equals(slotDetails.getSlotNumber()) &&
                slotRepository.existsBySlotNumber(slotDetails.getSlotNumber())) {
            throw new BadRequestException("Slot number '" + slotDetails.getSlotNumber() + "' is already taken!");
        }

        existingSlot.setSlotNumber(slotDetails.getSlotNumber());
        if (slotDetails.getFacilityName() != null) {
            existingSlot.setFacilityName(slotDetails.getFacilityName());
        }
        if (slotDetails.getAddress() != null) {
            existingSlot.setAddress(slotDetails.getAddress());
        }
        if (slotDetails.getCity() != null) {
            existingSlot.setCity(slotDetails.getCity());
        }
        existingSlot.setFloor(slotDetails.getFloor());
        existingSlot.setSlotType(slotDetails.getSlotType());
        existingSlot.setPricePerHour(slotDetails.getPricePerHour());
        if (slotDetails.getStatus() != null) {
            existingSlot.setStatus(slotDetails.getStatus());
        }

        return slotRepository.save(existingSlot);
    }

    public ParkingSlot updateSlotStatus(Long id, SlotStatus status) {
        ParkingSlot slot = getSlotById(id);
        slot.setStatus(status);
        return slotRepository.save(slot);
    }

    public void deleteSlot(Long id) {
        ParkingSlot slot = getSlotById(id);
        slotRepository.delete(slot);
    }
}

