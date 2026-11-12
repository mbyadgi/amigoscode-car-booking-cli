package com.mohi.car;

import com.mohi.exception.CarBookingException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarFileDataAccessService implements CarDao {
    private final String filePath;

    public CarFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] values = line.split(",");

                UUID carId = UUID.fromString(values[0].trim());
                String regNumber = values[1].trim();
                BigDecimal rentalPricePerDay = new BigDecimal(values[2].trim());
                Brand brand = Brand.valueOf(values[3].trim());
                boolean isElectric = Boolean.parseBoolean(values[4].trim());

                cars.add(new Car(carId, regNumber, rentalPricePerDay, brand, isElectric));
            }
        } catch (IOException e) {
            throw new CarBookingException("error while reading file: " + e.getMessage());
        }

        return cars;
    }

    @Override
    public Car findCarById(UUID carId) {
        for (Car car : getCars()) {
            if (carId.equals(car.getId())) {
                return car;
            }
        }
        return null;
    }
}