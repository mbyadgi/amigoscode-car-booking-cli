package com.mohi.booking;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private static CarBooking[] carBookings;
    private static int carBookingsCount = 0;

    @Override
    public CarBooking[] getCarBookings() {
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
        if (carBookings == null || carBookings.length == 0) {
            carBookings = new CarBooking[1];
        }
        if (carBookingsCount == carBookings.length) {
            carBookings = Arrays.copyOf(carBookings, carBookingsCount + 1);
        }
        carBookings[carBookingsCount] = carBooking;
        return carBookings[carBookingsCount++];
    }

    @Override
    public CarBooking cancelCarBooking(UUID carBookingId) {
        CarBooking carBooking = findCarBookingById(carBookingId);
        carBooking.setBookingStatus(BookingStatus.CANCELLED);
        return carBooking;
    }
}
