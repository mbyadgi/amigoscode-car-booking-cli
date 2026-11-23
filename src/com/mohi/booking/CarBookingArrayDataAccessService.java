package com.mohi.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private static final List<CarBooking> carBookings = new ArrayList<>();

    @Override
    public List<CarBooking> getCarBookings() {
        return carBookings;
    }

    @Override
    public Optional<CarBooking> findCarBookingById(UUID carBookingId) {
        return carBookings.stream().filter(carBooking -> carBookingId.equals(carBooking.getId())).findFirst();
    }

    @Override
    public CarBooking saveCarBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
        return carBookings.getLast();
    }

    @Override
    public CarBooking cancelCarBooking(UUID carBookingId) {
        CarBooking carBooking = findCarBookingById(carBookingId).get();
        carBooking.setBookingStatus(BookingStatus.CANCELLED);
        return carBooking;
    }
}