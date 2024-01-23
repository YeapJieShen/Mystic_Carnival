/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public class Customer extends User {

    private LocalDate dob;
    private Gender gender;
    private double credits = 0;
    private double totalCreditsBought = 0;
    private final ArrayList<Parking> PARKINGS = new ArrayList();
    private final ArrayList<Ticket> TICKETS = new ArrayList();

// Constructors
    public Customer() {
    }

    public Customer(String username, String password, String lastName, String firstName, LocalDate dob, Gender gender) {
        super(username, password, lastName, firstName);
        this.dob = dob;
        this.gender = gender;
    }

// Setters
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

// Getters
    public LocalDate getDOB() {
        return this.dob;
    }

    public double getCredits() {
        return this.credits;
    }

    public double getTotalCreditsBought() {
        return this.totalCreditsBought;
    }
    
// Level 1 Methods
    public void displayMenu(Schedule[] schedules, Vendor[] vendors) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("CUSTOMER MENU");
                System.out.println("=============");
                System.out.println();
                System.out.println("Welcome " + super.getLastName() + " !!");
                System.out.println("What would you like to do today !!");
                System.out.println();
                System.out.println("[1] Buy Ticket");
                System.out.println("[2] Buy Credits");
                System.out.println("[3] Rent Parking");
                System.out.println("[4] Enter Carnival");
                System.out.println("[5] My Inventory");
                System.out.println();
                System.out.println("Press [0] to LOGOUT");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> {
                        buyTicket(schedules);
                    }
                    case 2 -> {
                        buyCredits();
                    }
                    case 3 -> {
                        rentParking(schedules);
                    }
                    case 4 -> {
                        enterCarnival(schedules, vendors);
                    }
                    case 5 -> {
                        checkInventory();
                    }
                    case 0 -> {
                        System.out.println("Are you sure you want to logout");
                        System.out.println("[1] No");
                        System.out.println("[0] Yes");
                        System.out.println();
                        System.out.print("Your Choice: ");
                        choice = input.nextInt();
                        input.nextLine();

                        switch (choice) {
                            case 0 -> {
                                return;
                            }
                            case 1 -> {
                                continue;
                            }
                            default -> {
                                throw new InvalidChoiceException();
                            }
                        }
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid number !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void buyTicket(Schedule[] schedules) {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean isSameTicketBought = false;
        Schedule chosenSchedule;

        do {
            try {
                System.out.println("BUY TICKET");
                System.out.println();
                System.out.println(String.format("%5s%-12s%10s%-11s%17s", "", "Date", "Start Time", "End Time", "Tickets Available"));
                for (int i = 0; i < schedules.length; i++) {
                    System.out.println(String.format("%-5s%33s%17s",
                            String.format("[%d]", (i + 1)),
                            schedules[i].toString(),
                            String.format("%d",
                                    schedules[i].getNumAvailable("Tickets"))
                    ));
                }
                System.out.println();
                System.out.println("Press [0] to BACK");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                if (choice < 0 || choice > schedules.length) {
                    throw new InvalidChoiceException();
                } else if (choice == 0) {
                    return;
                } else {
                    chosenSchedule = schedules[choice - 1];
                    Ticket[] tickets = new Ticket[this.TICKETS.size()];
                    this.TICKETS.toArray(tickets);
                    if (Ticket.isSameScheduleTicketBought(tickets, chosenSchedule)) {
                        System.out.println("Ticket for this schedule has been bought !!");
                        System.out.println();
                        isSameTicketBought = true;
                        break;
                    } else if (chosenSchedule.getNumAvailable("Tickets") <= 0) {
                        throw new InsufficientStockException("No tickets available !!");
                    } else {
                        break;
                    }
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid choice !!");
            } catch (InvalidChoiceException | InsufficientStockException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        if (isSameTicketBought) {
            return;
        } else {
            Ticket[] chosenScheduleTickets = new Ticket[chosenSchedule.getTickets().size()];
            chosenSchedule.getTickets().toArray(chosenScheduleTickets);

            for (Ticket ticket : chosenScheduleTickets) {
                if (ticket.getTicketStatus().equals(Status.AVAILABLE)) {
                    ticket.sell();
                    this.TICKETS.add(ticket);
                    break;
                }

            }
        }
        int carParkingsAvailable = chosenSchedule.getNumAvailable("CarParkings");
        int motorParkingsAvailable = chosenSchedule.getNumAvailable("MotorParkings");

        boolean continueBuyParking = true;
        boolean carParkingBought = false;

        if (carParkingsAvailable > 0) {
            do {
                try {
                    System.out.println("Would you like to buy car parking?");
                    System.out.println("Number of Car Parkings Available: " + carParkingsAvailable);
                    System.out.println();
                    System.out.println("[1] Yes");
                    System.out.println("[0] No, maybe later");
                    System.out.println("Your choice: ");

                    choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {
                        case 1 -> {
                            Parking[] chosenScheduleParkings = new Parking[chosenSchedule.getParkings().size()];
                            chosenSchedule.getParkings().toArray(chosenScheduleParkings);
                            for (Parking parking : chosenScheduleParkings) {
                                if (parking instanceof CarParking carParking) {
                                    if (carParking.getParkingStatus().equals(Status.AVAILABLE)) {
                                        this.PARKINGS.add(carParking);
                                        carParking.sell();
                                        continueBuyParking = false;
                                        carParkingBought = true;
                                        break;
                                    }
                                }
                            }
                        }
                        case 0 -> {
                            continueBuyParking = false;
                            carParkingBought = false;
                            break;
                        }
                        default -> {
                            continueBuyParking = true;
                            throw new InvalidChoiceException();
                        }
                    }

                } catch (java.util.InputMismatchException e) {
                    input.nextLine();
                    System.out.println("Please enter a valid number !!");
                } catch (InvalidChoiceException e) {
                    System.out.println(e.getMessage());
                }
            } while (continueBuyParking);

        }

        if (motorParkingsAvailable > 0 && !carParkingBought) {
            do {
                try {
                    System.out.println("Would you like to buy motor parking ?");
                    System.out.println("Number of Motor Parkings Available: " + motorParkingsAvailable);
                    System.out.println();
                    System.out.println("[1] Yes");
                    System.out.println("[0] No, maybe later");

                    choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {
                        case 1 -> {
                            Parking[] chosenScheduleParkings = new Parking[chosenSchedule.getParkings().size()];
                            chosenSchedule.getParkings().toArray(chosenScheduleParkings);
                            for (Parking parking : chosenScheduleParkings) {
                                if (parking instanceof MotorParking motorParking) {
                                    if (motorParking.getParkingStatus().equals(Status.AVAILABLE)) {
                                        this.PARKINGS.add(motorParking);
                                        motorParking.sell();
                                        continueBuyParking = false;
                                        break;
                                    }
                                }
                            }
                        }
                        case 0 -> {
                            continueBuyParking = false;
                            break;
                        }
                        default -> {
                            continueBuyParking = true;
                            throw new InvalidChoiceException();
                        }
                    }

                } catch (java.util.InputMismatchException e) {
                    input.nextLine();
                    System.out.println("Please enter a valid number !!");
                } catch (InvalidChoiceException e) {
                    System.out.println(e.getMessage());
                }
            } while (continueBuyParking);
        }
    } //To improve

    public void buyCredits() {
        Scanner input = new Scanner(System.in);
        double amount;
        do {
            try {
                System.out.println();
                System.out.println("How much credit you would like to buy ?");
                System.out.println("Current credits (RM): " + String.format("%.2f", this.credits));
                System.out.println();
                System.out.println("Enter [0] to BACK");
                System.out.print("Enter amount: RM ");
                amount = input.nextDouble();
                input.nextLine();

                if (amount < 0) {
                    throw new InvalidChoiceException("Invalid amount !!");
                } else if (amount == 0) {
                    return;
                } else {
                    this.credits += amount;
                    this.totalCreditsBought += amount;
                    System.out.println();
                    System.out.println("Credits successfully bought !!");
                    System.out.println("Current credits: RM " + String.format("%.2f", this.credits));
                    System.out.println();
                    return;
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println();
                System.out.println("Invalid amount !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void rentParking(Schedule[] schedules) {
        Scanner input = new Scanner(System.in);
        ArrayList<Schedule> boughtSchedulesList = new ArrayList();
        int choice;

        Ticket[] tickets = new Ticket[this.TICKETS.size()];
        this.TICKETS.toArray(tickets);

        Parking[] parkings = new Parking[this.PARKINGS.size()];
        this.PARKINGS.toArray(parkings);

        for (Schedule schedule : schedules) {
            if (Ticket.isSameScheduleTicketBought(tickets, schedule)
                    && !Parking.isSameScheduleParkingBought(parkings, schedule)) {
                boughtSchedulesList.add(schedule);
            }

        }

        Schedule[] boughtSchedules = new Schedule[boughtSchedulesList.size()];
        boughtSchedulesList.toArray(boughtSchedules);
        Schedule chosenSchedule;
        boolean isSameParkingBought = false;

        do {
            try {
                System.out.println();
                System.out.println("Rent Parking");
                System.out.println("============");
                System.out.println();
                System.out.println("Parkings available");
                System.out.println();
                System.out.println(String.format("%4s%-12s%10s%10s%19s", "", "Date", "Start Time", "End Time", " Parkings Available"));
                System.out.println(String.format("%36s%-10s%-9s", "", "Car", "Motor"));
                for (int i = 0; i < boughtSchedules.length; i++) {
                    Schedule boughtSchedule = boughtSchedules[i];

                    System.out.println(String.format("%4s%-12s%10s%10s%9s%9s",
                            String.format("[%d]", (i + 1)),
                            boughtSchedule.getDate(),
                            String.format("%2d:%2d",
                                    boughtSchedule.getStartTime().getHour(),
                                    boughtSchedule.getStartTime().getMinute()),
                            String.format("%2d:%2d",
                                    boughtSchedule.getEndTime().getHour(),
                                    boughtSchedule.getEndTime().getMinute()),
                            String.format("%d", boughtSchedule.getNumAvailable("CarParkings")),
                            String.format("%d", boughtSchedule.getNumAvailable("MotorParkings"))));
                }
                System.out.println();
                System.out.println("Press [0] to BACK");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                if (choice < 0 || choice > boughtSchedules.length) {
                    throw new InvalidChoiceException();
                } else if (choice == 0) {
                    return;
                } else {
                    chosenSchedule = boughtSchedules[choice - 1];
                    if (Parking.isSameScheduleParkingBought(parkings, chosenSchedule)) {
                        System.out.println("Parking for this schedule has been bought !!");
                        System.out.println();
                        isSameParkingBought = true;
                        break;
                    } else if (chosenSchedule.getNumAvailable("CarParkings") <= 0 && chosenSchedule.getNumAvailable("MotorParkings") <= 0) {
                        throw new InsufficientStockException("No parkings available !!");
                    } else {
                        break;
                    }
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println();
                System.out.println("Please enter a valid choice !!");
            } catch (InvalidChoiceException | InsufficientStockException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        if (isSameParkingBought) {
            return;
        }

        do {
            try {
                System.out.println("What type of parking you would like to buy?");
                System.out.println("[1] Car parking");
                System.out.println("[2] Motor parking");
                System.out.println();
                System.out.println("Press [0] to CANCEL");
                System.out.print("Your choice: ");

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> {
                        if (chosenSchedule.getNumAvailable("CarParkings") <= 0) {
                            throw new InsufficientStockException("No car parkings available !!");
                        } else {
                            Parking[] chosenScheduleParkings = new Parking[chosenSchedule.getParkings().size()];
                            chosenSchedule.getParkings().toArray(chosenScheduleParkings);
                            for (Parking parking : chosenScheduleParkings) {
                                if (parking instanceof CarParking carParking) {
                                    if (carParking.getParkingStatus().equals(Status.AVAILABLE)) {
                                        this.PARKINGS.add(carParking);
                                        carParking.sell();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    case 2 -> {
                        if (chosenSchedule.getNumAvailable("MotorParkings") <= 0) {
                            throw new InsufficientStockException("No motor parkings available !!");
                        } else {
                            Parking[] chosenScheduleParkings = new Parking[chosenSchedule.getParkings().size()];
                            chosenSchedule.getParkings().toArray(chosenScheduleParkings);
                            for (Parking parking : chosenScheduleParkings) {
                                if (parking instanceof MotorParking motorParking) {
                                    if (motorParking.getParkingStatus().equals(Status.AVAILABLE)) {
                                        this.PARKINGS.add(motorParking);
                                        motorParking.sell();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid choice !!");
            } catch (InvalidChoiceException | InsufficientStockException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

    } //To improve

    public void enterCarnival(Schedule[] schedules, Vendor[] vendors) {
        Scanner input = new Scanner(System.in);
        int leavingChoice = 1;
        Schedule chosenSchedule;
        Stall[] participatingStalls;

        if (this.credits < 20) {
            System.out.println();
            System.out.println("You need at least RM 20.00 balance credits to join !!");
            return;
        } else {
            chosenSchedule = useTicket(schedules);

            if (chosenSchedule == null) {
                return;
            } else {
                useParking(chosenSchedule);
            }
        }

        Stall chosenStall;
        Transaction transaction;

        do {
            participatingStalls = chosenSchedule.displayMenu(vendors);
            chosenStall = chooseStall(participatingStalls);
            if (chosenStall == null) {
                do {
                    try {
                        System.out.println("Are you sure you want to leave ?");
                        System.out.println("[0] Leave");
                        System.out.println("[1] No Leave");
                        System.out.println();
                        System.out.print("Your Choice: ");
                        leavingChoice = input.nextInt();
                        input.nextLine();

                        switch (leavingChoice) {
                            case 0 -> {
                                return;
                            }
                            case 1 -> {
                                break;
                            }
                            default -> {
                                throw new InvalidChoiceException();
                            }
                        }
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter the correct choice !!");
                    } catch (InvalidChoiceException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);
            } else {
                do {
                    Product[] stallProducts = chosenStall.displayMenu();
                    transaction = buyProduct(stallProducts);

                    if (transaction == null) {
                        break;
                    } else {
                        chosenSchedule.getTransaction().add(transaction);
                    }
                } while (true);
            }
        } while (leavingChoice != 0);
    }

    public void checkInventory() {
        System.out.println();
        System.out.println(super.getLastName() + "'s Inventory");
        System.out.println();
        System.out.println("Owned Tickets:");
        System.out.println(String.format("%15s%-10s%-10s", "", "Ticket ID", "Status"));
        for (Ticket ticket : this.TICKETS) {
            System.out.println(ticket.toString());
        }
        System.out.println();
        System.out.println("Owned Parkings:");
        System.out.println(String.format("%15s%-11s%-10s", "", "Parking ID", "Status"));
        for (Parking parking : PARKINGS) {
            System.out.println(parking.toString());
        }
        System.out.println();
        System.out.println("Current credits (RM): " + String.format("%.2f", this.credits));
        System.out.println();
    }

// Level 2 Methods
    @Override
    public void displayMenu(ArrayList<Schedule> schedulesList, Vendor[] vendors, Customer[] customers) {
        Schedule[] schedules = new Schedule[schedulesList.size()];
        schedulesList.toArray(schedules);
        displayMenu(schedules, vendors);
    }

    public Schedule useTicket(Schedule[] schedules) {
        Scanner input = new Scanner(System.in);
        String enteredId;
        Ticket chosenTicket = null;
        boolean isTicketFound = false;

        do {
            try {

                System.out.print("Enter your ticket id: ");
                enteredId = input.nextLine();
                for (Ticket ticket : this.TICKETS) {
                    if (ticket.getTicketId().equals(enteredId)) {
                        chosenTicket = ticket;
                        isTicketFound = true;
                        break;
                    }
                }
                if (isTicketFound) {
                    try {
                        for (Schedule schedule : schedules) {
                            if (schedule.validateEntrance(chosenTicket)) {
                                return schedule;
                            }
                        }
                        throw new TicketInvalidException("No valid ticket found !!");

                    } catch (TicketInvalidException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    throw new TicketInvalidException("No valid ticket found !!");
                }
            } catch (TicketInvalidException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } while (true);
    }

    public Stall chooseStall(Stall[] participatingStalls) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.print("Enter your Choice:");
                choice = input.nextInt();
                input.nextLine();
                if (choice < 0 || choice > participatingStalls.length) {
                    throw new InvalidChoiceException();
                } else {
                    if (choice == 0) {
                        return null;
                    } else {
                        return participatingStalls[choice - 1];
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter a number !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public Transaction buyProduct(Product[] stallProducts) {
        Scanner input = new Scanner(System.in);
        int choice, quantity;

        do {
            try {
                System.out.print("Enter your Choice: ");
                choice = input.nextInt();
                input.nextLine();
                if (choice < 0 || choice > stallProducts.length) {
                    throw new InvalidChoiceException();
                } else if (choice == 0) {
                    return null;
                } else {
                    boolean isBuying = false;
                    Product product = stallProducts[choice - 1];

                    do {
                        try {
                            System.out.print("Enter quantity: ");
                            quantity = input.nextInt();
                            input.nextLine();
                            if (quantity <= 0) {
                                isBuying = true;
                                throw new InvalidChoiceException();
                            } else if (quantity > product.getStock()) {
                                isBuying = true;
                                throw new InsufficientStockException("Insufficient Stock !!");
                            } else {
                                double subTotal = quantity * product.getUnitPrice();
                                if (subTotal > this.credits) {
                                    isBuying = false;
                                    throw new InsufficientCreditException("Insufficient Credits");
                                } else {
                                    product.setStock(product.getStock() - quantity);
                                    spendCredits(subTotal);
                                    System.out.println();
                                    System.out.println("Transaction successful !!");
                                    System.out.println();
                                    System.out.println("You spent " + String.format("RM %.2f", subTotal));
                                    System.out.println("You have " + String.format("RM %.2f left", this.credits));
                                    return new Transaction(product.getProductId(), quantity);
                                }
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Please enter a number");
                        } catch (InvalidChoiceException | InsufficientCreditException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (isBuying);
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter a number");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void spendCredits(double amount) {
        this.credits -= amount;
    }

    public void useParking(Schedule chosenSchedule) {
        for (Parking parking : this.PARKINGS) {
            if (parking.getParkingId().startsWith(String.format("%d", chosenSchedule.getId()))) {
                parking.setParkingStatus(Status.USED);
                return;
            }
        }
    }

// Static Functionalities
}
