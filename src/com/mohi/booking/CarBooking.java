package com.mohi.booking;

import com.mohi.car.Car;
import com.mohi.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {
    private UUID id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus bookingStatus;
    private LocalDateTime bookedAt;

    public CarBooking(UUID id, User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus bookingStatus, LocalDateTime bookedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.bookingStatus = bookingStatus;
        this.bookedAt = bookedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    @Override
    public String toString() {
        return "CarBooking {" +
                "\n bookingId = " + id +
                "\n user = " + user +
                "\n car = " + car +
                "\n startDate = " + startDate +
                "\n endDate = " + endDate +
                "\n price = " + price +
                "\n bookingStatus = " + bookingStatus +
                "\n bookedAt = " + bookedAt +
                "\n}";
    }
}