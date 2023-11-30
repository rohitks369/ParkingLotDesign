package test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.InvalidVehicleNumberException;
import exception.ParkingFullException;
import model.Slot;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import service.ParkingLot;
import service.ParkingSystemInterface;
import strategy.FourWheelerWeekDayStrategy;
import strategy.ParkingChargeStrategy;
import strategy.TwoWheelerWeekDayStrategy;

public class ParkingSystem implements ParkingSystemInterface {
	private ParkingLot parkingLot;

    public ParkingSystem() {
        this.parkingLot = ParkingLot.getParkingLot();
    }
    
	@Override
	public void parkVehicle(Scanner scanner) throws ParkingFullException {
		System.out.println("Enter vehicle number:");
		String vehicleNumber = scanner.next();

		VehicleSize vehicleSize = getVehicleSizeFromUserInput(scanner);

		Vehicle vehicle = new Vehicle(vehicleNumber, vehicleSize);

		Ticket ticket = parkingLot.park(vehicle);
		System.out.println("Parked : " + ticket);
		
	}

	@Override
	public void unparkVehicle(Scanner scanner) throws InvalidVehicleNumberException {
		System.out.println("Enter ticket details for unparking:");

		System.out.println("Enter vehicle number from the ticket:");
		String ticketVehicleNumber = scanner.next();

		// Get vehicle size from user input
		VehicleSize ticketVehicleSize = getVehicleSizeFromUserInput(scanner);

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Get the day of the week from the current date
		DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

		Vehicle vehicle = new Vehicle(ticketVehicleNumber, ticketVehicleSize);

		Ticket unparkTicket = parkingLot.getTicketDetails(ticketVehicleNumber);
		
		int parkingCost = parkingLot.upPark(unparkTicket, getCostStrategy(ticketVehicleSize, dayOfWeek));

		System.out.println("Unparked. Parking cost: " + parkingCost);
	}

	private VehicleSize getVehicleSizeFromUserInput(Scanner scanner) {
		// Display options for vehicle size
		System.out.println("Choose a vehicle size:");
		System.out.println("1. TWOWHEELER");
		System.out.println("2. FOURWHEELER");

		int userChoice = scanner.nextInt();

		switch (userChoice) {
		case 1:
			return VehicleSize.TWOWHEELER;
		case 2:
			return VehicleSize.FOURWHEELER;
		default:
			System.out.println("Invalid choice. Defaulting to TWOWHEELER.");
			return VehicleSize.TWOWHEELER;
		}
	}

	private ParkingChargeStrategy getCostStrategy(VehicleSize vehicleSize, DayOfWeek dayOfWeek)
			throws InvalidVehicleNumberException {
		switch (vehicleSize) {
		case TWOWHEELER:
			return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? new TwoWheelerWeekDayStrategy()
					: new TwoWheelerWeekDayStrategy();
		case FOURWHEELER:
			return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? new FourWheelerWeekDayStrategy()
					: new FourWheelerWeekDayStrategy();
		default:
			throw new InvalidVehicleNumberException("Invalid vehicle size: " + vehicleSize);
		}
	}
}
