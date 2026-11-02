package com.mohi.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("b120a6bb-f367-4be8-acf2-d3ae65bdd4a5"), "MCL1012", new BigDecimal("65.90"), Brand.BMW, false),
                new Car(UUID.fromString("9d76c20d-87d0-4d15-85f2-0359238e4ba9"), "SCD9876", new BigDecimal("111.58"), Brand.AUDI, false),
                new Car(UUID.fromString("a13d17e7-61b4-428a-9908-94b28ea6070c"), "KZ4321", new BigDecimal("179.61"), Brand.MERCEDES, false),
                new Car(UUID.fromString("82be4566-ca7d-4246-a1a8-d515b910d958"), "DNU246", new BigDecimal("84.66"), Brand.TESLA, false),
                new Car(UUID.fromString("1d352f7f-a3d1-416a-aabe-e2e71a86fe94"), "NRE808", new BigDecimal("115.55"), Brand.TOYOTA, false),
                new Car(UUID.fromString("f9043674-fe62-412f-89ec-bc7a8bafe294"), "LAM77", new BigDecimal("85.06"), Brand.BMW, false),
                new Car(UUID.fromString("8ba4ccd5-3bfe-4f49-82a9-5828f6adabd3"), "BXY789E", new BigDecimal("172.13"), Brand.AUDI, true),
                new Car(UUID.fromString("1249f8e9-070f-4f00-a3c9-429fcc91d397"), "MAB1234E", new BigDecimal("108.47"), Brand.MERCEDES, true),
                new Car(UUID.fromString("56408b42-dcec-4bfc-8c77-deb618794248"), "FEV555E", new BigDecimal("135.45"), Brand.TESLA, true),
                new Car(UUID.fromString("75e90769-c394-42d0-b3d6-40d271ca599c"), "HHGO42E", new BigDecimal("174.56"), Brand.TOYOTA, true)
        };
    }

    public Car[] getCars() {
        return cars;
    }

    public Car getCarById(UUID carId) {
        for (Car car : cars) {
            if (carId.equals(car.getId())) {
                return car;
            }
        }
        return null;
    }
}