package com.mohi.booking;

import com.mohi.car.Car;
import com.mohi.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] carBookings;
    private static int carBookingsCount = 0;

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public CarBooking getCarBookingById(UUID carBookingId) {
        for (CarBooking carBooking : carBookings) {
            if (carBookingId.equals(carBooking.getId())) {
                return carBooking;
            }
        }
        return null;
    }

    public CarBooking saveCarBooking(User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus bookingStatus, LocalDateTime bookedAt) {
        if (carBookings == null || carBookings.length == 0) {
            carBookings = new CarBooking[1];
        }
        if (carBookingsCount == carBookings.length) {
            carBookings = Arrays.copyOf(carBookings, carBookingsCount + 1);
        }
        carBookings[carBookingsCount] = new CarBooking(UUID.randomUUID(), user, car, startDate, endDate, price, bookingStatus, bookedAt);
        return carBookings[carBookingsCount++];
    }

    public CarBooking cancelCarBooking(CarBooking carBooking) {
        carBooking.setBookingStatus(BookingStatus.CANCELLED);
        return carBooking;
    }
}