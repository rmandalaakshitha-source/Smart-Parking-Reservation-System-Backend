package com.parking.system.repository;

import com.parking.system.model.Reservation;
import com.parking.system.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Reservation> findByParkingSlotIdAndStatus(Long slotId, ReservationStatus status);
    
    @Query("SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.status != 'CANCELLED'")
    Double calculateTotalRevenue();
}
