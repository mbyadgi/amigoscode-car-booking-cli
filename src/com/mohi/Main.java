package com.mohi;

import com.mohi.booking.BookingStatus;
import com.mohi.booking.CarBooking;
import com.mohi.booking.CarBookingService;
import com.mohi.car.Car;
import com.mohi.car.CarService;
import com.mohi.exception.CarBookingException;
import com.mohi.user.User;
import com.mohi.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    static void main() {
        UserService userService = new UserService();
        CarService carService = new CarService();
        CarBookingService carBookingService = new CarBookingService();

        Scanner scanner = new Scanner(System.in);

        StringBuilder menu = new StringBuilder();
        menu.append("Please choose an option from below:").append("\n")
                .append("1 - Book Car").append("\n")
                .append("2 - Delete Booking").append("\n")
                .append("3 - View All User Booked Cars").append("\n")
                .append("4 - View All Bookings").append("\n")
                .append("5 - View Available Cars").append("\n")
                .append("6 - View Available Electric Cars").append("\n")
                .append("7 - View All Users").append("\n")
                .append("8 - Exit").append("\n");

        while (true) {
            int option = 0;
            System.out.println("\n" + menu);

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> bookCar(carBookingService, scanner);
                    case 2 -> deleteBooKing(carBookingService, scanner);
                    case 3 -> displayCarsBookedByUser(carBookingService, userService, scanner);
                    case 4 -> displayCarBookings(carBookingService);
                    case 5 -> displayAvailableCars(carService, carBookingService, false);
                    case 6 -> displayAvailableCars(carService, carBookingService, true);
                    case 7 -> displayUsers(userService);
                    case 8 -> System.out.println("Good Bye!");
                    default -> throw new CarBookingException("Invalid option provided!!");
                }
            } catch (InputMismatchException | IllegalArgumentException | CarBookingException e) {
                String errorMsg;
                if (e.getClass().getSimpleName().equalsIgnoreCase("InputMismatchException")) {
                    errorMsg = "Invalid option provided!!";
                } else {
                    errorMsg = e.getMessage();
                }
                System.out.println("Error: " + errorMsg);
                scanner.nextLine();
            }

            if (option == 8) break;
        }

        scanner.close();
    }

    private static void bookCar(CarBookingService carBookingService, Scanner scanner) {
        System.out.println("Please provide User ID:");
        UUID userId = UUID.fromString(scanner.next());

        System.out.println("Please provide Car ID:");
        UUID carId = UUID.fromString(scanner.next());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate;
        LocalDate endDate;
        try {
            System.out.println("Please provide start date in dd/MM/yyyy format:");
            startDate = LocalDate.parse(scanner.next(), formatter);

            System.out.println("Please provide end date in dd/MM/yyyy format:");
            endDate = LocalDate.parse(scanner.next(), formatter);
        } catch (DateTimeParseException e) {
            throw new CarBookingException("Incorrect value provided for date");
        }

        CarBooking carBooking = carBookingService.bookCar(userId, carId, startDate, endDate);
        System.out.println("Booking completed successfully!!");
        System.out.println(carBooking);
    }

    private static void deleteBooKing(CarBookingService carBookingService, Scanner scanner) {
        if (carBookingService.noActiveCarBookingsExist()) {
            System.out.println("No Active Car Bookings found");
            return;
        }
        System.out.println("Please provide Booking ID:");
        UUID bookingId = UUID.fromString(scanner.next());
        CarBooking carBooking = carBookingService.cancelCarBooking(bookingId);
        System.out.println("Booking cancelled successfully!!");
        System.out.println(carBooking);
    }

    private static void displayCarsBookedByUser(CarBookingService carBookingService, UserService userService, Scanner scanner) {
        if (carBookingService.noCarBookingsExist()) {
            System.out.println("No Car Bookings found");
            return;
        }

        System.out.println("Please provide User ID:");
        UUID userId = UUID.fromString(scanner.next());

        User user = userService.getUserById(userId);
        if (user == null) throw new CarBookingException("No User found with ID: " + userId);

        boolean noCarsBooked = true;
        for (CarBooking carBooking : carBookingService.getCarBookings()) {
            if (carBooking.getUser().equals(user) && carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)) {
                System.out.println(carBooking.getCar());
                noCarsBooked = false;
            }
        }
        if (noCarsBooked) {
            System.out.println("No cars booked by the user");
        }
    }

    private static void displayCarBookings(CarBookingService carBookingService) {
        if (carBookingService.noCarBookingsExist()) {
            System.out.println("No Car Bookings found");
            return;
        }
        for (CarBooking carBooking : carBookingService.getCarBookings()) {
            System.out.println(carBooking);
        }
    }

    private static void displayAvailableCars(CarService carService, CarBookingService carBookingService, boolean displayElectricCars) {
        boolean noCarsAvailable = true;
        for (Car car : carService.getCars()) {
            if (displayElectricCars) {
                if (car.isElectric() && !carBookingService.isCarBooked(car)) {
                    System.out.println(car);
                    noCarsAvailable = false;
                }
            } else if (!carBookingService.isCarBooked(car)) {
                System.out.println(car);
                noCarsAvailable = false;
            }
        }
        if (noCarsAvailable) {
            System.out.println("No Cars available to book");
        }
    }

    private static void displayUsers(UserService userService) {
        for (User user : userService.getUsers()) {
            System.out.println(user);
        }
    }
}