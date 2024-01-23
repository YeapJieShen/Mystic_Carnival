/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

/**
 *
 * @author Jie Shen
 */
public class Slot implements Purchasable {

    private String slotId;
    private Status slotStatus;

    public Slot() {
        this(0, 0);
    }

    public Slot(int scheduleId, int idCount) {
        slotId = scheduleId + "SL" + idCount;
        slotStatus = Status.AVAILABLE;
    }

    public String getSlotId() {
        return slotId;
    }

    public Status getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(Status slotStatus) {
        this.slotStatus = slotStatus;
    }

    @Override
    public String toString() {
        return String.format("\n%26s%-19s%2s%s", "", "Slot Id", ": ", this.slotId)
                + String.format("\n%26s%-19s%2s", "", "Status", ": ") + this.slotStatus;

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Slot s) {
            return this.slotId.equals(s.slotId);
        }
        return false;
    }

    @Override
    public void sell() {
        this.slotStatus = Status.VALID;
        System.out.println("Slot successfully bought");
        System.out.println("Slot ID: " + this.slotId);
    }
}
