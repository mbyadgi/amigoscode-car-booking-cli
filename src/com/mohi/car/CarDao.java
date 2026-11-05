package com.mohi.car;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarDao {
    Car[] getCars();
    Car findCarById(UUID carId);
}