/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public class Schedule {

    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private int maxTickets;
    private int maxMotorParkings;
    private int maxCarParkings;
    private int maxSlots;

    private static int ticketLimit = 50;
    private static int motorParkingsLimit = 10;
    private static int carParkingsLimit = 20;
    private static int slotsLimit = 10;

    private final ArrayList<Ticket> TICKETS = new ArrayList();
    private final ArrayList<Parking> PARKINGS = new ArrayList();
    private final ArrayList<Slot> SLOTS = new ArrayList();

    private final ArrayList<Transaction> TRANSACTIONS = new ArrayList();

// Constructors
    public Schedule() {
    }

    public Schedule(LocalDate date, LocalTime startTime, LocalTime endTime, int maxTickets, int maxMotorParkings, int maxCarParkings, int maxSlots) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

        this.maxTickets = maxTickets;
        this.maxMotorParkings = maxMotorParkings;
        this.maxCarParkings = maxCarParkings;
        this.maxSlots = maxSlots;

        if (startTime.getHour() < 12) {
            this.id = date.getYear() % 100 * 100000 + date.getMonthValue() * 1000 + date.getDayOfMonth() * 10 + 1;
        } else {
            this.id = date.getYear() % 100 * 100000 + date.getMonthValue() * 1000 + date.getDayOfMonth() * 10 + 2;
        }

        for (int i = 0; i < this.maxTickets; i++) {
            this.TICKETS.add(new Ticket(this.id, i + 1));
        }
        for (int i = 0; i < this.maxCarParkings; i++) {
            this.PARKINGS.add(new CarParking(this.id, i + 1));
        }
        for (int i = 0; i < this.maxMotorParkings; i++) {
            this.PARKINGS.add(new MotorParking(this.id, i + 1));
        }
        for (int i = 0; i < this.maxSlots; i++) {
            this.SLOTS.add(new Slot(this.id, i + 1));
        }
    }

// Setters
    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public void setMaxBikeParkings(int maxBikeParkings) {
        this.maxMotorParkings = maxBikeParkings;
    }

    public void setMaxCarParkings(int maxCarParkings) {
        this.maxCarParkings = maxCarParkings;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

// Getters
    public int getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public ArrayList<Ticket> getTickets() {
        return this.TICKETS;
    }

    public ArrayList<Parking> getParkings() {
        return this.PARKINGS;
    }

    public ArrayList<Slot> getSlots() {
        return SLOTS;
    }

    public ArrayList<Transaction> getTransaction() {
        return this.TRANSACTIONS;
    }

    public int getMaxTickets() {
        return this.maxTickets;
    }

    public int getMaxCarParkings() {
        return this.maxCarParkings;
    }

    public int getMaxMotorParkings() {
        return this.maxMotorParkings;
    }

    public int getMaxSlots() {
        return this.maxSlots;
    }
    
    public static int getTicketsLimit(){
        return Schedule.ticketLimit;
    }
    
    public static int getCarParkingsLimit(){
        return Schedule.carParkingsLimit;
    }

    public static int getMotorParkingsLimit(){
        return Schedule.motorParkingsLimit;
    }
    
    public static int getSlotsLimit(){
        return Schedule.slotsLimit;
    }
    
// Methods
    public Stall[] displayMenu(Vendor[] vendors) {
        boolean isStallFound;
        ArrayList<Stall> stallsList = new ArrayList();

        for (Vendor vendor : vendors) {
            Stall[] vendorStalls = new Stall[vendor.getStalls().size()];
            vendor.getStalls().toArray(vendorStalls);

            for (Stall vendorStall : vendorStalls) {
                stallsList.add(vendorStall);
            }
        }

        Stall[] stalls = new Stall[stallsList.size()];
        stallsList.toArray(stalls);

        ArrayList<Stall> participatingStallsList = new ArrayList();

        for (Stall stall : stalls) {
            Slot[] slots = new Slot[stall.getSlots().size()];
            stall.getSlots().toArray(slots);
            for (Slot slot : slots) {
                if (slot.getSlotId().startsWith(String.format("%d", this.id))) {
                    participatingStallsList.add(stall);
                    break;
                }
            }
        }

        Stall[] participatingStalls = new Stall[participatingStallsList.size()];
        participatingStallsList.toArray(participatingStalls);

        System.out.println();
        System.out.println("Welcome to the carnival !!");
        System.out.println("Which stall would you like to visit ?");
        System.out.println();
        System.out.println(String.format("%-5s%-5s%-20s", "", "Type", "Stall Name"));
        for (int i = 0; i < participatingStalls.length; i++) {
            System.out.println(String.format("%-5s%-5s%-20s",
                    String.format("[%d]", (i + 1)),
                    participatingStalls[i].getStallType(),
                    participatingStalls[i].getStallName()));

        }
        System.out.println("Press [0] to BACK");

        return participatingStalls;
    }

    public int getNumAvailable(String type) {
        int numAvailable = 0;
        switch (type) {
            case "Tickets" -> {
                for (Ticket ticket : this.TICKETS) {
                    if (ticket.getTicketStatus().equals(Status.AVAILABLE)) {
                        numAvailable++;
                    }
                }
            }
            case "CarParkings" -> {
                for (Parking parking : this.PARKINGS) {
                    if (parking instanceof CarParking && parking.getParkingStatus().equals(Status.AVAILABLE)) {
                        numAvailable++;
                    }
                }
            }
            case "MotorParkings" -> {
                for (Parking parking : this.PARKINGS) {
                    if (parking instanceof MotorParking && parking.getParkingStatus().equals(Status.AVAILABLE)) {
                        numAvailable++;
                    }
                }
            }
            case "Slots" -> {
                for (Slot slot : this.SLOTS) {
                    if (slot.getSlotStatus().equals(Status.AVAILABLE)) {
                        numAvailable++;
                    }
                }
            }
            case "Transactions" -> {
                numAvailable = this.TRANSACTIONS.size();
            }
        }
        return numAvailable;
    }

    public void edit() {
        Scanner input = new Scanner(System.in);
        char choice;

        do {
            try {
                System.out.println();
                System.out.println("Editing Schedule: " + this.toString());
                System.out.println("What would you like to edit");
                System.out.println("[S] Slots");
                System.out.println("[T] Tickets");
                System.out.println("[C] Car Parkings");
                System.out.println("[M] Motor Parkings");
                System.out.println();
                System.out.println("Press [B] to BACK");
                System.out.println("Your choice: ");

                choice = Character.toUpperCase(input.nextLine().charAt(0));

                switch (choice) {
                    case 'S' -> {
                        this.maxSlots = editNumOf("slots", (this.maxSlots - getNumAvailable("Slots")), Schedule.slotsLimit);
                        break;
                    }
                    case 'T' -> {
                        this.maxTickets = editNumOf("tickets", (this.maxTickets - getNumAvailable("Tickets")), Schedule.ticketLimit);
                        break;
                    }
                    case 'C' -> {
                        this.maxCarParkings = editNumOf("car parkings", (this.maxCarParkings - getNumAvailable("CarParkings")), Schedule.carParkingsLimit);
                        break;
                    }
                    case 'M' -> {
                        this.maxMotorParkings = editNumOf("motor parkings", (this.maxMotorParkings - getNumAvailable("MotorParkings")), Schedule.motorParkingsLimit);
                        break;
                    }
                    case 'B' -> {
                        return;
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid choice !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public int editNumOf(String type, int numSold, int limit) {
        Scanner input = new Scanner(System.in);
        int newNum;

        do {
            try {
                System.out.println("Editing " + type);
                System.out.println("Enter new number (between " + numSold + " and " + limit + " only)");

                newNum = input.nextInt();
                input.nextLine();

                if (newNum < numSold || newNum > limit) {
                    throw new InvalidChoiceException();
                } else {
                    return newNum;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid choice");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

//  Static Functionalities
    public boolean validateEntrance(Ticket ticket) {
        for (Ticket scheduleTicket : this.TICKETS) {
            if (scheduleTicket.equals(ticket) && ticket.getTicketStatus() == Status.VALID) {
                scheduleTicket.setTicketStatus(Status.USED);
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicateSchedule(Schedule[] schedules, LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (Schedule schedule : schedules) {
            if (schedule.date.equals(date)) {
                if (startTime.isBefore(schedule.startTime)) {
                    if (endTime.isAfter(schedule.startTime)) {
                        return true;
                    }
                } else if (startTime.isBefore(schedule.endTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%-12s%10s%11s",
                this.date,
                String.format("%2d:%2d",
                        this.startTime.getHour(),
                        this.startTime.getMinute()),
                String.format("%2d:%2d",
                        this.endTime.getHour(),
                        this.endTime.getMinute())
        );
    }
}
