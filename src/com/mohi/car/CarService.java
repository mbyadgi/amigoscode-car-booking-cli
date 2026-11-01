package com.mohi.car;

import java.util.UUID;

public class CarService {
    private final CarDao carDao;

    public CarService() {
        this.carDao = new CarDao();
    }

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car getCarById(UUID carId) {
        return carDao.getCarById(carId);
    }
}