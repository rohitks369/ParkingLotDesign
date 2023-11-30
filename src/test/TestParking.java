package test;

import java.util.Scanner;

import exception.InvalidVehicleNumberException;
import exception.ParkingFullException;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import service.ParkingLot;
import service.ParkingSystemInterface;
import strategy.FourWheelerWeekDayStrategy;
import strategy.TwoWheelerWeekDayStrategy;

public class TestParking {

	public static void main(String[] args) throws ParkingFullException, InvalidVehicleNumberException {
		ParkingLot parkingLot = ParkingLot.getParkingLot();

		parkingLot.initializeParkingSlots(10, 10);

		Scanner scanner = new Scanner(System.in);

		ParkingSystem parkingSystem=new ParkingSystem() ;

		boolean exit = false;
		while (!exit) {
			System.out.println("Choose an option:");
			System.out.println("1. Park a vehicle");
			System.out.println("2. Unpark a vehicle");
			System.out.println("3. Exit");

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				parkingSystem.parkVehicle(scanner);
				break;

			case 2:
				parkingSystem.unparkVehicle(scanner);
				break;
			case 3:
				exit = true;
				System.out.println("Exiting the parking system. Goodbye!");
				break;
			default:
				System.out.println("Invalid option");
				break;

			}
		}
	}

}
