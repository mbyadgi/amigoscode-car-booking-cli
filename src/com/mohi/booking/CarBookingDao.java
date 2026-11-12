package com.mohi.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {
    List<CarBooking> getCarBookings();

    CarBooking findCarBookingById(UUID carBookingId);

    CarBooking saveCarBooking(CarBooking carBooking);

    CarBooking cancelCarBooking(UUID carBookingId);
}