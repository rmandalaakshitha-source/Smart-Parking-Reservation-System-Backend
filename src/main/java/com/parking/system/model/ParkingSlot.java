package com.parking.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Slot number is required")
    @Column(nullable = false, unique = true)
    private String slotNumber;

    @NotBlank(message = "Facility name is required")
    @Column(nullable = false)
    private String facilityName = "Sample Parking Facility A";

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address = "123 Sample Location Street";

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city = "Sample City";

    @NotBlank(message = "Floor is required")
    @Column(nullable = false)
    private String floor;

    @Column(nullable = false)
    private String slotType; // e.g. Regular, Compact, EV Charging, Disabled

    @NotNull(message = "Price per hour is required")
    @Column(nullable = false)
    private Double pricePerHour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;

    public ParkingSlot() {
        this.status = SlotStatus.AVAILABLE;
        this.facilityName = "Sample Parking Facility A";
        this.address = "123 Sample Location Street";
        this.city = "Sample City";
    }

    public ParkingSlot(String slotNumber, String floor, String slotType, Double pricePerHour, SlotStatus status) {
        this.slotNumber = slotNumber;
        this.floor = floor;
        this.slotType = slotType;
        this.pricePerHour = pricePerHour;
        this.status = status != null ? status : SlotStatus.AVAILABLE;
        this.facilityName = "Sample Parking Facility A";
        this.address = "123 Sample Location Street";
        this.city = "Sample City";
    }

    public ParkingSlot(String slotNumber, String facilityName, String address, String city, String floor, String slotType, Double pricePerHour, SlotStatus status) {
        this.slotNumber = slotNumber;
        this.facilityName = facilityName;
        this.address = address;
        this.city = city;
        this.floor = floor;
        this.slotType = slotType;
        this.pricePerHour = pricePerHour;
        this.status = status != null ? status : SlotStatus.AVAILABLE;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }
}

