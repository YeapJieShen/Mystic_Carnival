/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

/**
 *
 * @author Jie Shen
 */
public class Ticket implements Purchasable {

    private String ticketId;
    private Status ticketStatus = Status.AVAILABLE;

    private static double price = 10;

    public Ticket() {
        this.ticketId = null;
    }

    public Ticket(int scheduleId, int count) {
        this.ticketId = scheduleId + "T" + count;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Status getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Status ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public static double getPrice() {
        return Ticket.price;
    }

    @Override
    public String toString() {
        return String.format("%15s%-10s%-10s",
                "",
                this.ticketId,
                this.ticketStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ticket t) {
            return this.ticketId.equals(t.ticketId);
        }

        return false;
    }

    @Override
    public void sell() {
        this.ticketStatus = Status.VALID;
        System.out.println("Ticket successfully bought !!");
        System.out.println("Ticket ID: " + this.ticketId);
    }

    public static boolean isSameScheduleTicketBought(Ticket[] custTickets, Schedule chosenSchedule) {
        for (Ticket ticket : custTickets) {
            if (ticket.ticketId.startsWith(String.format("%d", chosenSchedule.getId()))) {
                return true;
            }
        }
        return false;
    }
}
