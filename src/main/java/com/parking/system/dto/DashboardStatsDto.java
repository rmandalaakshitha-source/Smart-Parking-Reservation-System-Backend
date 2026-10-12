package com.parking.system.dto;

public class DashboardStatsDto {

    private long totalSlots;
    private long availableSlots;
    private long reservedSlots;
    private long occupiedSlots;
    private long totalReservations;
    private double totalRevenue;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long totalSlots, long availableSlots, long reservedSlots, long occupiedSlots, long totalReservations, double totalRevenue) {
        this.totalSlots = totalSlots;
        this.availableSlots = availableSlots;
        this.reservedSlots = reservedSlots;
        this.occupiedSlots = occupiedSlots;
        this.totalReservations = totalReservations;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(long totalSlots) {
        this.totalSlots = totalSlots;
    }

    public long getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(long availableSlots) {
        this.availableSlots = availableSlots;
    }

    public long getReservedSlots() {
        return reservedSlots;
    }

    public void setReservedSlots(long reservedSlots) {
        this.reservedSlots = reservedSlots;
    }

    public long getOccupiedSlots() {
        return occupiedSlots;
    }

    public void setOccupiedSlots(long occupiedSlots) {
        this.occupiedSlots = occupiedSlots;
    }

    public long getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(long totalReservations) {
        this.totalReservations = totalReservations;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
