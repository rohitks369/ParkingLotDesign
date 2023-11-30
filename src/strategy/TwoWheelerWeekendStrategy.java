package strategy;

public class TwoWheelerWeekendStrategy implements ParkingChargeStrategy{

	@Override
	public int getCharge(int parkHours) {
		if(parkHours<1) {
			return 15;
		}
		return parkHours*15;
	}
	
}
