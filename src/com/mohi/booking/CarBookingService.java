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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao;
    private final UserService userService;
    private final CarService carService;

    public CarBookingService(CarBookingDao carBookingDao, UserService userService, CarService carService) {
        this.carBookingDao = carBookingDao;
        this.userService = userService;
        this.carService = carService;
    }

    public List<CarBooking> getCarBookings() {
        return carBookingDao.getCarBookings();
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty()) throw new CarBookingException("No User found with ID: " + userId);

        Optional<Car> car = carService.findCarById(carId);
        if (car.isEmpty()) throw new CarBookingException("No Car found with ID: " + carId);

        if (startDate.isBefore(LocalDate.now())) throw new CarBookingException("start date cannot be in past");
        if (!endDate.isAfter(startDate)) throw new CarBookingException("end date must be after start date");

        if (isCarBooked(car.get()))
            throw new CarBookingException("The Car is not available. Delete the booking with this car to make it available.");

        long bookingDays = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal price = car.get().getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingDays));
        return carBookingDao.saveCarBooking(new CarBooking(UUID.randomUUID(), user.get(), car.get(), startDate, endDate, price, BookingStatus.ACTIVE, LocalDateTime.now()));
    }

    public CarBooking cancelCarBooking(UUID carBookingId) {
        Optional<CarBooking> carBooking = carBookingDao.findCarBookingById(carBookingId);
        if (carBooking.isEmpty()) throw new CarBookingException("No booking found for ID: " + carBookingId);
        if (carBooking.get().getBookingStatus().equals(BookingStatus.CANCELLED))
            throw new CarBookingException("Booking is already Cancelled");
        return carBookingDao.cancelCarBooking(carBookingId);
    }

    public List<Car> getCarsBookedByUser(UUID userId) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty()) throw new CarBookingException("No User found with ID: " + userId);

        return carBookingDao.getCarBookings().stream()
                .filter(carBooking -> carBooking.getUser().equals(user.get()))
                .filter(carBooking -> carBooking.getBookingStatus().equals(BookingStatus.ACTIVE))
                .map(CarBooking::getCar)
                .toList();
    }

    public List<Car> getAvailableCars() {
        List<UUID> bookedCarIds = carBookingDao.getCarBookings().stream()
                .filter(carBooking -> carBooking.getBookingStatus().equals(BookingStatus.ACTIVE))
                .map(carBooking -> carBooking.getCar().getId())
                .toList();

        if (bookedCarIds.isEmpty()) return carService.getCars();

        return carService.getCars().stream()
                .filter(car -> !bookedCarIds.contains(car.getId()))
                .toList();
    }

    public List<Car> getAvailableElectricCars() {
        return getAvailableCars().stream()
                .filter(Car::isElectric)
                .toList();
    }

    public boolean noActiveCarBookingsExist() {
        return carBookingDao.getCarBookings().stream()
                .noneMatch(carBooking -> carBooking.getBookingStatus().equals(BookingStatus.ACTIVE));
    }

    private boolean isCarBooked(Car car) {
        return carBookingDao.getCarBookings().stream()
                .anyMatch(carBooking -> carBooking.getCar().getId().equals(car.getId()) &&
                        carBooking.getBookingStatus().equals(BookingStatus.ACTIVE));
    }
}