package com.mohi.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private static final List<CarBooking> carBookings = new ArrayList<>();

    @Override
    public List<CarBooking> getCarBookings() {
        return carBookings;
    }

    @Override
    public CarBooking findCarBookingById(UUID carBookingId) {
        for (CarBooking carBooking : carBookings) {
            if (carBookingId.equals(carBooking.getId())) {
                return carBooking;
            }
        }
        return null;
    }

    @Override
    public CarBooking saveCarBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
        return carBookings.getLast();
    }

    @Override
    public CarBooking cancelCarBooking(UUID carBookingId) {
        CarBooking carBooking = findCarBookingById(carBookingId);
        carBooking.setBookingStatus(BookingStatus.CANCELLED);
        return carBooking;
    }
}