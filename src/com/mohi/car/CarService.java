package com.mohi.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getCars() {
        return carDao.getCars();
    }

    public Optional<Car> findCarById(UUID carId) {
        return carDao.findCarById(carId);
    }
}