package com.mohi.booking;

import com.mohi.exception.CarBookingException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<CarBooking> getCarBookings() {
        List<CarBooking> carBookings = new ArrayList<>();

        if (Files.exists(Path.of(filePath))) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                Object readObject = inputStream.readObject();
                if (readObject instanceof List<?>) {
                    carBookings = (List<CarBooking>) readObject;
                } else if (readObject instanceof CarBooking[]) {
                    carBookings = new ArrayList<>(Arrays.asList((CarBooking[]) readObject));
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new CarBookingException("Failed to read car bookings from " + filePath + e.getMessage());
            }
        }

        return carBookings;
    }

    @Override
    public Optional<CarBooking> findCarBookingById(UUID carBookingId) {
        return getCarBookings().stream()
                .filter(carBooking -> carBookingId.equals(carBooking.getId()))
                .findFirst();
    }

    @Override
    public CarBooking saveCarBooking(CarBooking carBooking) {
        List<CarBooking> carBookings = getCarBookings();

        carBookings.add(carBooking);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(carBookings);
        } catch (IOException e) {
            throw new CarBookingException("error while saving car booking: " + e.getMessage());
        }

        return carBookings.getLast();
    }

    @Override
    public CarBooking cancelCarBooking(UUID carBookingId) {
        List<CarBooking> carBookings = getCarBookings();
        CarBooking cancelledBooking = carBookings.stream()
                .filter(carBooking -> carBookingId.equals(carBooking.getId()))
                .findFirst().get();
        cancelledBooking.setBookingStatus(BookingStatus.CANCELLED);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(carBookings);
        } catch (IOException e) {
            throw new CarBookingException("error while saving car booking: " + e.getMessage());
        }

        return cancelledBooking;
    }
}