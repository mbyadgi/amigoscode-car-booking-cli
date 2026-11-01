package com.mohi.booking;

import com.mohi.car.Car;
import com.mohi.car.CarService;
import com.mohi.exception.CarBookingException;
import com.mohi.user.User;
import com.mohi.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao;
    private final UserService userService;
    private final CarService carService;

    public CarBookingService() {
        this.carBookingDao = new CarBookingDao();
        this.userService = new UserService();
        this.carService = new CarService();
    }

    public CarBooking[] getCarBookings() {
        return carBookingDao.getCarBookings();
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        User user = userService.getUserById(userId);
        if (user == null) throw new CarBookingException("No User found with ID: " + userId);

        Car car = carService.getCarById(carId);
        if (car == null) throw new CarBookingException("No Car found with ID: " + carId);

        if (startDate.isBefore(LocalDate.now())) throw new CarBookingException("start date cannot be in past");
        if (!endDate.isAfter(startDate)) throw new CarBookingException("end date must be after start date");

        if (isCarBooked(car))
            throw new CarBookingException("The Car is not available. Delete the booking with this car to make it available.");

        long bookingDays = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal price = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingDays));
        return carBookingDao.saveCarBooking(user, car, startDate, endDate, price, BookingStatus.ACTIVE, LocalDateTime.now());
    }

    public CarBooking cancelCarBooking(UUID carBookingId) {
        CarBooking carBooking = carBookingDao.getCarBookingById(carBookingId);
        if (carBooking == null) throw new CarBookingException("No booking found for ID: " + carBookingId);
        if (carBooking.getBookingStatus().equals(BookingStatus.CANCELLED)) throw new CarBookingException("Booking is already Cancelled");
        return carBookingDao.cancelCarBooking(carBooking);
    }

    public boolean isCarBooked(Car car) {
        if (noCarBookingsExist()) return false;
        for (CarBooking carBooking : carBookingDao.getCarBookings()) {
            if (carBooking.getCar().equals(car) && carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)) {
                return true;
            }
        }
        return false;
    }

    public boolean noActiveCarBookingsExist() {
        if (noCarBookingsExist()) return true;
        for (CarBooking carBooking : carBookingDao.getCarBookings()){
            if(carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)){
                return false;
            }
        }
        return true;
    }

    public boolean noCarBookingsExist() {
        return carBookingDao.getCarBookings() == null || carBookingDao.getCarBookings().length == 0;
    }
}