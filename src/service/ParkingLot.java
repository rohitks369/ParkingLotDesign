package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.InvalidVehicleNumberException;
import exception.ParkingFullException;
import model.Slot;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import strategy.ParkingChargeStrategy;

public class ParkingLot implements Parking {
	private static ParkingLot parkingLot;

	private final List<Slot> twoWheelerSlots;
	private final List<Slot> fourWheelerSlots;
	private final HashMap<String,Ticket> ticketDetails;

	public ParkingLot() {
		this.twoWheelerSlots = new ArrayList<Slot>();
		this.fourWheelerSlots = new ArrayList<Slot>();
		ticketDetails=new HashMap<>();
	}

	public static ParkingLot getParkingLot() {
		if (parkingLot == null) {
			parkingLot = new ParkingLot();
		}
		return parkingLot;
	}

	public boolean initializeParkingSlots(int numberOfTwoWheelerSlots, int numberOfFourWheelerSlots) {

		for (int i = 1; i < numberOfTwoWheelerSlots; i++) {
			twoWheelerSlots.add(new Slot(i));
		}

		for (int i = 1; i < numberOfFourWheelerSlots; i++) {
			fourWheelerSlots.add(new Slot(i));
		}
		return true;
	}

	@Override
	public Ticket park(Vehicle vehicle) throws ParkingFullException {
		Slot nextAvailableSlot;

		if (vehicle.getVehicleSize().equals(VehicleSize.FOURWHEELER)) {
			nextAvailableSlot = getNextAvailableFourWheelerSlot();
		} else {
			nextAvailableSlot = getNextAvailableTwoWheelerSlot();
		}

		nextAvailableSlot.occupySlot(vehicle);

		System.out.println("Allocated Slot Number : " + nextAvailableSlot.getSlotNumber());

		Ticket ticket = new Ticket(nextAvailableSlot.getSlotNumber(), vehicle.getVehicleNumber(), new Date(),
				vehicle.getVehicleSize());
		
		ticketDetails.put(vehicle.getVehicleNumber(), ticket);
		return ticket;
	}

	private Slot getNextAvailableFourWheelerSlot() throws ParkingFullException {
		for (Slot slot : fourWheelerSlots) {
			if (slot.isEmpty()) {
				return slot;
			}
		}
		throw new ParkingFullException("No Empty Slot available");

	}

	private Slot getNextAvailableTwoWheelerSlot() throws ParkingFullException {
		for (Slot slot : twoWheelerSlots) {
			if (slot.isEmpty()) {
				return slot;
			}
		}
		throw new ParkingFullException("No Empty Slot available");

	}

	// unpark Logic
	@Override
	public int upPark(Ticket ticket, ParkingChargeStrategy parkingChargeStrategy) throws InvalidVehicleNumberException {
		int costPerHours = 0;
		Slot slot;

		try {
			if (ticket.getVehicleSize().equals(VehicleSize.FOURWHEELER)) {
				slot = getFourWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
			} else {
				slot = getTwoWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
			}
			slot.vacateSlot();
			ticketDetails.remove(ticket.getVehicleNumber());

			int hours = getHoursParked(ticket.getDate(), new Date());
			costPerHours = parkingChargeStrategy.getCharge(hours);
			

			System.out.println("Vehicle with Registration : " + ticket.getVehicleNumber() + " at slot number : "
					+ slot.getSlotNumber()+" was parked for : "+hours+" hours and the total charge is : "+costPerHours);

		} catch (InvalidVehicleNumberException invalidVehicleNumberException) {
			System.out.println(invalidVehicleNumberException.getMessage());
			throw invalidVehicleNumberException;
		}
		return costPerHours;
	}

	private int getHoursParked(Date startDate, Date endDate) {
		long secs = (endDate.getTime() - startDate.getTime()) / 1000;
		int hours = (int) (secs / 3600);

		return hours;
	}

	private Slot getFourWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException {
		for (Slot slot : fourWheelerSlots) {
			Vehicle vehicle = slot.getParkVehicle();
			if (vehicle != null && vehicle.getVehicleNumber().equals(vehicleNumber)) {
				return slot;
			}
		}
		throw new InvalidVehicleNumberException(
				"Four Wheeler with Registration Number " + vehicleNumber + " not found !!");
	}

	private Slot getTwoWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException {
		for (Slot slot : twoWheelerSlots) {
			Vehicle vehicle = slot.getParkVehicle();
			if (vehicle != null && vehicle.getVehicleNumber().equals(vehicleNumber)) {
				return slot;
			}
		}
		throw new InvalidVehicleNumberException(
				"Four Wheeler with Registration Number " + vehicleNumber + " not found !!");
	}
	
	public Ticket getTicketDetails(String vehicleNumber)  throws InvalidVehicleNumberException{
        if(ticketDetails.containsKey(vehicleNumber)) {
        	return ticketDetails.get(vehicleNumber);
        }
        
        throw new InvalidVehicleNumberException("Invaild Vehicle Number");
    }
}

