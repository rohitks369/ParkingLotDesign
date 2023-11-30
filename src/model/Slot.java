package model;

public class Slot {
	private int slotNumber;
	private boolean isEmpty;
	private Vehicle parkVehicle;

	public Slot(int slotNumber) {
		super();
		this.slotNumber = slotNumber;
		isEmpty = true;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Vehicle getParkVehicle() {
		return parkVehicle;
	}

	public void setParkVehicle(Vehicle parkVehicle) {
		this.parkVehicle = parkVehicle;
	}
	
	//vacate slot
	public void vacateSlot() {
		this.parkVehicle=null;
		this.isEmpty=true;
	}
	
	//unvacate slot
	public void occupySlot(Vehicle parkVehicle) {
		this.parkVehicle=parkVehicle;
		this.isEmpty=false;
	}

}
