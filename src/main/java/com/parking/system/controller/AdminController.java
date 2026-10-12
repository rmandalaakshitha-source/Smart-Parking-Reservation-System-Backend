package com.parking.system.controller;

import com.parking.system.dto.DashboardStatsDto;
import com.parking.system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://127.0.0.1:5173", "http://127.0.0.1:5174" })
public class AdminController {

    private final ReservationService reservationService;

    @Autowired
    public AdminController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        DashboardStatsDto stats = reservationService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
