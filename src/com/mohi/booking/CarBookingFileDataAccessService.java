package com.mohi.booking;

import com.mohi.exception.CarBookingException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CarBooking[] getCarBookings() {
        CarBooking[] carBookings = new CarBooking[0];

        if(Files.exists(Path.of(filePath))) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                carBookings = (CarBooking[]) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new CarBookingException(e.getMessage());
            }
        }

        return carBookings;
    }

    @Override
    public CarBooking findCarBookingById(UUID carBookingId) {
        for (CarBooking carBooking : getCarBookings()) {
            if (carBookingId.equals(carBooking.getId())) {
                return carBooking;
            }
        }
        return null;
    }

    @Override
    public CarBooking saveCarBooking(CarBooking carBooking) {
        CarBooking[] carBookings = getCarBookings();

        int oldLength = carBookings.length;
        carBookings = Arrays.copyOf(carBookings, oldLength + 1);
        carBookings[oldLength] = carBooking;

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))){
            outputStream.writeObject(carBookings);
        } catch (IOException e) {
            throw new CarBookingException("error while saving car booking: " + e.getMessage());
        }

        return carBookings[oldLength];
    }

    @Override
    public CarBooking cancelCarBooking(UUID carBookingId) {
        CarBooking[] carBookings = getCarBookings();
        CarBooking cancelledBooking = null;

        for (CarBooking savedBooking : carBookings) {
            if(savedBooking.getId().equals(carBookingId)){
                savedBooking.setBookingStatus(BookingStatus.CANCELLED);
                cancelledBooking = savedBooking;
                break;
            }
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))){
            outputStream.writeObject(carBookings);
        } catch (IOException e) {
            throw new CarBookingException("error while saving car booking: " + e.getMessage());
        }

        return cancelledBooking;
    }
}
