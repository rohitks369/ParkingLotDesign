package test;

import exception.InvalidVehicleNumberException;
import exception.ParkingFullException;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import service.ParkingLot;
import strategy.FourWheelerWeekDayStrategy;
import strategy.TwoWheelerWeekDayStrategy;

public class TestParking {

	public static void main(String[] args) throws ParkingFullException, InvalidVehicleNumberException {
		ParkingLot parkingLot = ParkingLot.getParkingLot();

		parkingLot.initializeParkingSlots(10, 10);

		// for two wheeler
		Vehicle twoWheelerVehicle1 = new Vehicle("WB12", VehicleSize.TWOWHEELER);
		Ticket twoWheelerTicket1 = parkingLot.park(twoWheelerVehicle1);
		System.out.println("Ticket : " + twoWheelerTicket1);
		
		
		Vehicle twoWheelerVehicle2 = new Vehicle("WB13", VehicleSize.TWOWHEELER);
		Ticket twoWheelerTicket2 = parkingLot.park(twoWheelerVehicle2);
		System.out.println("Ticket : " + twoWheelerTicket2);

		
		// for four wheeler
		Vehicle fourWheelerVehicle = new Vehicle("WB14", VehicleSize.FOURWHEELER);

		Ticket fourWheelerTicket = parkingLot.park(fourWheelerVehicle);

		System.out.println("Ticket : " + fourWheelerTicket);
		
		//unpark logic
		System.out.println("\n\n");
		int twoWheelerParkingCost=parkingLot.upPark(twoWheelerTicket2, new TwoWheelerWeekDayStrategy());
		System.out.println("COST : "+twoWheelerParkingCost);
		
		int fourWheelerParkingCost=parkingLot.upPark(fourWheelerTicket, new FourWheelerWeekDayStrategy());
		System.out.println("COST : "+fourWheelerParkingCost);
	}

}
