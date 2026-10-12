package com.parking.system.service;

import com.parking.system.dto.DashboardStatsDto;
import com.parking.system.dto.ReservationRequest;
import com.parking.system.exception.BadRequestException;
import com.parking.system.exception.ResourceNotFoundException;
import com.parking.system.model.*;
import com.parking.system.repository.ParkingSlotRepository;
import com.parking.system.repository.ReservationRepository;
import com.parking.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSlotRepository slotRepository;

    @Autowired
    public ReservationService(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            ParkingSlotRepository slotRepository) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.slotRepository = slotRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequest request) {

        if (request.getEndTime().isBefore(request.getStartTime())
                || request.getEndTime().isEqual(request.getStartTime())) {

            throw new BadRequestException(
                    "End time must be strictly after start time.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + request.getUserId()));

        ParkingSlot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Parking slot not found with id: " + request.getSlotId()));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new BadRequestException(
                    "Parking slot " + slot.getSlotNumber()
                            + " is currently " + slot.getStatus() + "!");
        }

        // Calculate parking duration and total price
        long minutes = Duration.between(
                request.getStartTime(),
                request.getEndTime()).toMinutes();

        double hours = Math.max(
                1.0,
                Math.ceil(minutes / 60.0));

        double totalPrice = hours * slot.getPricePerHour();

        // Create reservation
        Reservation reservation = new Reservation(
                user,
                slot,
                request.getVehicleNumber(),
                request.getStartTime(),
                request.getEndTime(),
                totalPrice,
                ReservationStatus.CONFIRMED);

        // Change parking slot status
        slot.setStatus(SlotStatus.RESERVED);
        slotRepository.save(slot);

        // Save reservation immediately
        reservationRepository.saveAndFlush(reservation);

        return reservation;
    }

    public List<Reservation> getUserReservations(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return reservationRepository
                .findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found with id: " + id));
    }

    @Transactional
    public Reservation cancelReservation(Long id) {

        Reservation reservation = getReservationById(id);

        if (reservation.getStatus() == ReservationStatus.CANCELLED
                || reservation.getStatus() == ReservationStatus.COMPLETED) {

            throw new BadRequestException(
                    "Reservation cannot be cancelled because it is already "
                            + reservation.getStatus() + ".");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        ParkingSlot slot = reservation.getParkingSlot();

        if (slot != null) {
            slot.setStatus(SlotStatus.AVAILABLE);
            slotRepository.save(slot);
        }

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateStatus(
            Long id,
            ReservationStatus status) {

        Reservation reservation = getReservationById(id);

        reservation.setStatus(status);

        ParkingSlot slot = reservation.getParkingSlot();

        if (slot != null) {

            switch (status) {

                case COMPLETED:
                case CANCELLED:
                    slot.setStatus(SlotStatus.AVAILABLE);
                    break;

                case OCCUPIED:
                    slot.setStatus(SlotStatus.OCCUPIED);
                    break;

                case CONFIRMED:
                    slot.setStatus(SlotStatus.RESERVED);
                    break;

                default:
                    break;
            }

            slotRepository.save(slot);
        }

        return reservationRepository.save(reservation);
    }

    public DashboardStatsDto getDashboardStats() {

        long totalSlots = slotRepository.count();

        long availableSlots = slotRepository.countByStatus(SlotStatus.AVAILABLE);

        long reservedSlots = slotRepository.countByStatus(SlotStatus.RESERVED);

        long occupiedSlots = slotRepository.countByStatus(SlotStatus.OCCUPIED);

        long totalReservations = reservationRepository.count();

        Double totalRevenue = reservationRepository.calculateTotalRevenue();

        return new DashboardStatsDto(
                totalSlots,
                availableSlots,
                reservedSlots,
                occupiedSlots,
                totalReservations,
                totalRevenue != null ? totalRevenue : 0.0);
    }
}