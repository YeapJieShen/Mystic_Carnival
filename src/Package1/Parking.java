/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

/**
 *
 * @author Jie Shen
 */
public abstract class Parking implements Purchasable {

    private String parkingId;
    private Status parkingStatus;

    public String getParkingId() {
        return parkingId;
    }

    public Status getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public void setParkingStatus(Status parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    @Override
    public String toString() {
        return String.format("%15s%-11s",
                "",
                this.parkingId
        ) + this.parkingStatus;
    }

    @Override
    public void sell() {
        this.parkingStatus = Status.VALID;
        System.out.println();
        System.out.println("Parking successfully bought !!");
        System.out.println();
        System.out.println("Parking ID: " + this.parkingId);
    }

    public static boolean isSameScheduleParkingBought(Parking[] custParkings, Schedule chosenSchedule) {
        for (Parking parking : custParkings) {
            if (parking.parkingId.startsWith(String.format("%d", chosenSchedule.getId()))
                    && parking.parkingStatus == Status.VALID) {
                return true;
            }
        }
        return false;
    }
}

class CarParking extends Parking {

    public CarParking() {
        this(0, 0);
    }

    public CarParking(int scheduleId, int idCount) {
        String parkingId = scheduleId + "CP" + idCount;
        super.setParkingId(parkingId);
        super.setParkingStatus(Status.AVAILABLE);

    }

    @Override
    public String getParkingId() {
        return super.getParkingId();
    }

    @Override
    public Status getParkingStatus() {
        return super.getParkingStatus();
    }

    @Override
    public void setParkingStatus(Status parkingStatus) {
        super.setParkingStatus(parkingStatus);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class MotorParking extends Parking {

    public MotorParking() {
        this(0, 0);
    }

    public MotorParking(int scheduleId, int idCount) {
        String parkingId = scheduleId + "MP" + idCount;
        super.setParkingId(parkingId);
        super.setParkingStatus(Status.AVAILABLE);

    }

    @Override
    public String getParkingId() {
        return super.getParkingId();
    }

    @Override
    public Status getParkingStatus() {
        return super.getParkingStatus();
    }

    @Override
    public void setParkingStatus(Status parkingStatus) {
        super.setParkingStatus(parkingStatus);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
