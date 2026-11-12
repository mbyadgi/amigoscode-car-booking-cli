package com.mohi.car;

import java.util.List;
import java.util.UUID;

public interface CarDao {
    List<Car> getCars();

    Car findCarById(UUID carId);
}