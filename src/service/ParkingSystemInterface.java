package service;

import java.util.Scanner;

import exception.InvalidVehicleNumberException;
import exception.ParkingFullException;

public interface ParkingSystemInterface {
	void parkVehicle(Scanner scanner) throws ParkingFullException;

	void unparkVehicle(Scanner scanner) throws InvalidVehicleNumberException;
}
