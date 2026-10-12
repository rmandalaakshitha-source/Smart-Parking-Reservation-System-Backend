package com.parking.system.config;

import com.parking.system.model.*;
import com.parking.system.repository.ParkingSlotRepository;
import com.parking.system.repository.ReservationRepository;
import com.parking.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ParkingSlotRepository slotRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           ParkingSlotRepository slotRepository,
                           ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.slotRepository = slotRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed default users if empty
        if (userRepository.count() == 0) {
            User admin = new User("System Admin", "admin@parking.com", "admin123", "+19876543210", Role.ADMIN);
            User user = new User("John Doe", "user@parking.com", "user123", "+1234567890", Role.USER);
            userRepository.saveAll(Arrays.asList(admin, user));
            System.out.println(">>> Sample Users initialized: admin@parking.com / admin123, user@parking.com / user123");
        }

        // Seed default parking slots if empty
        if (slotRepository.count() == 0) {
            List<ParkingSlot> slots = Arrays.asList(
                    // Sample Parking Facility A
                    new ParkingSlot("A-101", "Sample Parking Facility A", "123 Sample Location Street, Block 4", "Sample City", "Ground Floor", "Regular", 15.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("A-102", "Sample Parking Facility A", "123 Sample Location Street, Block 4", "Sample City", "Ground Floor", "EV Charging", 25.0, SlotStatus.RESERVED),
                    new ParkingSlot("A-103", "Sample Parking Facility A", "123 Sample Location Street, Block 4", "Sample City", "Ground Floor", "Disabled", 10.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("A-104", "Sample Parking Facility A", "123 Sample Location Street, Block 4", "Sample City", "1st Floor", "Compact", 12.0, SlotStatus.OCCUPIED),
                    
                    // Sample Parking Facility B
                    new ParkingSlot("B-201", "Sample Parking Facility B", "456 Sample Avenue, Tech Zone", "Sample City", "Ground Floor", "Regular", 18.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("B-202", "Sample Parking Facility B", "456 Sample Avenue, Tech Zone", "Sample City", "Ground Floor", "EV Charging", 28.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("B-203", "Sample Parking Facility B", "456 Sample Avenue, Tech Zone", "Sample City", "1st Floor", "Compact", 14.0, SlotStatus.RESERVED),
                    new ParkingSlot("B-204", "Sample Parking Facility B", "456 Sample Avenue, Tech Zone", "Sample City", "2nd Floor", "SUV", 22.0, SlotStatus.AVAILABLE),

                    // Sample Parking Facility C
                    new ParkingSlot("C-301", "Sample Parking Facility C", "789 Sample Boulevard, Center Hub", "Sample City", "Basement 1", "Regular", 20.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("C-302", "Sample Parking Facility C", "789 Sample Boulevard, Center Hub", "Sample City", "Basement 1", "SUV", 25.0, SlotStatus.OCCUPIED),
                    new ParkingSlot("C-303", "Sample Parking Facility C", "789 Sample Boulevard, Center Hub", "Sample City", "Ground Floor", "Disabled", 12.0, SlotStatus.AVAILABLE),
                    new ParkingSlot("C-304", "Sample Parking Facility C", "789 Sample Boulevard, Center Hub", "Sample City", "Ground Floor", "Regular", 15.0, SlotStatus.AVAILABLE)
            );
            slotRepository.saveAll(slots);
            System.out.println(">>> Sample Parking Slots initialized: 12 slots across Sample Facilities A, B & C");

            // Seed sample reservations for demonstration
            User sampleUser = userRepository.findByEmail("user@parking.com").orElse(null);
            ParkingSlot slotA102 = slotRepository.findBySlotNumber("A-102").orElse(null);
            ParkingSlot slotB203 = slotRepository.findBySlotNumber("B-203").orElse(null);

            if (sampleUser != null && slotA102 != null && slotB203 != null) {
                Reservation res1 = new Reservation(
                        sampleUser,
                        slotA102,
                        "KA-01-AB-1234",
                        LocalDateTime.now().plusHours(1),
                        LocalDateTime.now().plusHours(3),
                        50.0,
                        ReservationStatus.CONFIRMED
                );

                Reservation res2 = new Reservation(
                        sampleUser,
                        slotB203,
                        "MH-12-CD-5678",
                        LocalDateTime.now().minusHours(4),
                        LocalDateTime.now().minusHours(2),
                        28.0,
                        ReservationStatus.COMPLETED
                );

                reservationRepository.saveAll(Arrays.asList(res1, res2));
                System.out.println(">>> Sample Reservations initialized");
            }
        }
    }
}

