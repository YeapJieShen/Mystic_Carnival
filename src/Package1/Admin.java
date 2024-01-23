/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public final class Admin extends User {

    private static final Admin ONLYADMIN = new Admin();

//    Constructors
    private Admin() {
        super("MysticAdmin", "IMaDMIN0!!", "GanGan", "Jing");
    }

//    Singleton
    public static Admin getInstance() {
        return Admin.ONLYADMIN;
    }

// Level 1 Methods
    @Override
    public void displayMenu(ArrayList<Schedule> schedulesList, Vendor[] vendors, Customer[] customers) {
        Scanner input = new Scanner(System.in);
        int choice;
        Schedule[] schedules = new Schedule[schedulesList.size()];
        schedulesList.toArray(schedules);
        System.out.println("Welcome Admin " + super.getLastName());

        do {
            try {
                System.out.println();
                System.out.println("Please enter what you would like to do !!");
                System.out.println("[1] Manage schedules");
                System.out.println("[2] Check statistics");
                System.out.println("[3] Check Analysis");
                System.out.println();
                System.out.println("Press [0] to LOGOUT");
                System.out.print("Your choice: ");

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> {
                        manageSchedules(schedulesList);
                    }
                    case 2 -> {
                        checkStatistics(schedules);
                    }
                    case 3 -> {
                        checkAnalysis(schedules, vendors, customers);
                    }
                    case 0 -> {
                        System.out.println("Are you sure you want to logout");
                        System.out.println("[0] Leave");
                        System.out.println("[1] No Leave");
                        System.out.println();
                        System.out.println("Your Choice: ");
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

    public void manageSchedules(ArrayList<Schedule> schedulesList) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("MANAGE SCHEDULES");
                System.out.println("================");
                System.out.println();
                System.out.println("Please enter what you would like to do");
                System.out.println("[1] Create Schedule");
                System.out.println("[2] Edit Schedule");
                System.out.println();
                System.out.println("Enter [0] to BACK");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> {
                        createSchedule(schedulesList);
                    }
                    case 2 -> {
                        editSchedule(schedulesList);
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
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void checkStatistics(Schedule[] schedules) {
        Scanner input = new Scanner(System.in);

        System.out.println("CHECK STATISTICS");
        System.out.println("================");
        System.out.println();
        System.out.println(String.format("%-12s%-12s%11s%11s", "Shcedule ID", "Date", "Start Time", "End Time"));
        for (Schedule schedule : schedules) {
            System.out.println(String.format("%-12s%32s",
                    String.format("%d", schedule.getId()),
                    schedule.toString()));
        }

        System.out.print("Enter a schedule ID to check statistics:");
        int scheduleId = input.nextInt();
        System.out.println("\nStatistic Report");
        System.out.println("================\n");
        for (Schedule schedule : schedules) {
            if (scheduleId == schedule.getId()) {
                LocalDate date = schedule.getDate();
                System.out.println("Date: " + date);

                LocalTime startTime = schedule.getStartTime();
                System.out.println("Start Time: " + startTime);

                LocalTime endTime = schedule.getEndTime();
                System.out.println("End Time: " + endTime);

                int availableTicket = schedule.getNumAvailable("Tickets");
                int maxTicket = schedule.getMaxTickets();
                double ticketSold = (double) (maxTicket - availableTicket) / (double) maxTicket * 100;
                System.out.println("Percentage of Ticket Sold: " + String.format("%.2f", ticketSold) + "%");

                int availableCParking = schedule.getNumAvailable("CarParkings");
                int maxCParking = schedule.getMaxCarParkings();
                double CPRented = (double) (maxCParking - availableCParking) / maxCParking * 100;
                System.out.println("Percentage of Car Parking Rented: " + String.format("%.2f", CPRented) + "%");

                int availableMParking = schedule.getNumAvailable("MotorParkings");
                int maxMParking = schedule.getMaxMotorParkings();
                double MPRented = (double) (maxMParking - availableMParking) / maxMParking * 100;
                System.out.println("Percentage of Motor Parking Rented: " + String.format("%.2f", MPRented) + "%");

                int availableSlot = schedule.getNumAvailable("Slots");
                int maxSlot = schedule.getMaxSlots();
                double slotRented = (double) (maxSlot - availableSlot) / maxSlot * 100;
                System.out.println("Percentage of Slot Rented: " + String.format("%.2f", slotRented) + "%");

                break;
            }
        }
    }

    public void checkAnalysis(Schedule[] schedules, Vendor[] vendors, Customer[] customers) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("CHECK ANALYSIS");
                System.out.println("==============");
                System.out.println();
                System.out.println("Please enter what you would like to do");
                System.out.println("[1] Buying Power of Each Age Group");
                System.out.println("[2] Most Popular Day of Week");
                System.out.println("[3] Most Popular Product Category");
                System.out.println();
                System.out.println("Enter [0] to BACK");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> {
                        buyingPower(customers);
                    }
                    case 2 -> {
                        mostPopularDayOfWeek(schedules);
                    }
                    case 3 -> {
                        mostPopularProductCategory(schedules, vendors);
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid choice");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
    
// Level 2 Methods
    public void createSchedule(ArrayList<Schedule> schedulesList) {
        Scanner input = new Scanner(System.in);
        Schedule[] schedules = new Schedule[schedulesList.size()];
        schedulesList.toArray(schedules);

        LocalDate date;
        LocalTime startTime, endTime;

        do {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                date = LocalDate.parse(input.nextLine());

                System.out.print("Enter start time (24HH:MM:SS): ");
                startTime = LocalTime.parse(input.nextLine());

                System.out.print("Enter end time (24HH:MM:SS): ");
                endTime = LocalTime.parse(input.nextLine());

                if (date.isBefore(LocalDate.now())) {
                    throw new InvalidScheduleTimeException("Date can not be earlier than today");
                } else if (startTime.isAfter(endTime)) {
                    throw new InvalidScheduleTimeException("End time can not be earlier than start time");
                } else if (Schedule.checkDuplicateSchedule(schedules, date, startTime, endTime)) {
                    throw new InvalidScheduleTimeException("Overlapping schedule time");
                } else {
                    break;
                }
            } catch (DateTimeException e) {
                System.out.println("Wrong time format!");
            } catch (InvalidScheduleTimeException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        int maxTickets = Admin.setMaxNumOf("tickets", Schedule.getTicketsLimit());
        int maxCarParkings = Admin.setMaxNumOf("car parkings", Schedule.getCarParkingsLimit());
        int maxBikeParkings = Admin.setMaxNumOf("bike parkings", Schedule.getMotorParkingsLimit());
        int maxSlots = Admin.setMaxNumOf("slots", Schedule.getSlotsLimit());
        System.out.println();

        schedulesList.add(new Schedule(date, startTime, endTime, maxTickets, maxBikeParkings, maxCarParkings, maxSlots));
    }

    public void editSchedule(ArrayList<Schedule> schedulesList) {
        Scanner input = new Scanner(System.in);
        int choice;

        ArrayList<Schedule> futureSchedulesList = new ArrayList();

        for (Schedule schedule : schedulesList) {
            if (schedule.getDate().isAfter(LocalDate.now())) {
                futureSchedulesList.add(schedule);
            }
        }

        Schedule[] futureSchedules = new Schedule[futureSchedulesList.size()];
        futureSchedulesList.toArray(futureSchedules);
        Schedule chosenSchedule;

        do {
            try {
                System.out.println(String.format("%4s%-12s%11s%10s", "", "Date", "Start Time", "End Time"));
                for (int i = 0; i < futureSchedules.length; i++) {
                    System.out.println(String.format("%4s%32s",
                            String.format("[%d]", (i + 1)),
                            futureSchedules[i].toString()
                    ));
                }
                System.out.println();
                System.out.println("Press [0] to BACK");
                System.out.print("Your Choice: ");

                choice = input.nextInt();
                input.nextLine();

                if (choice < 0 || choice > futureSchedules.length) {
                    throw new InvalidChoiceException();
                } else if (choice == 0) {
                    return;
                } else {
                    chosenSchedule = futureSchedules[choice - 1];
                    break;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid choice");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        chosenSchedule.edit();
    }

    public void buyingPower(Customer[] customers) {
        System.out.println("\nBuying Power Of Each Age Group");
        System.out.println("==============================\n");
        //Below 20,21-30,31-40,41-50,above 50
        double totalCredits = 0;
        double[] creditSpent = {0, 0, 0, 0, 0};
        double[] percentageCreditSpent = {0, 0, 0, 0, 0};

        for (Customer customer : customers) {
            totalCredits += customer.getTotalCreditsBought();

            LocalDate currentDate = LocalDate.now();
            int age = Period.between(customer.getDOB(), currentDate).getYears();

            if (age < 20) {
                creditSpent[0] += customer.getTotalCreditsBought();
            } else if (age >= 20 && age < 30) {
                creditSpent[1] += customer.getTotalCreditsBought();
            } else if (age >= 30 && age < 40) {
                creditSpent[2] += customer.getTotalCreditsBought();
            } else if (age >= 40 && age < 50) {
                creditSpent[3] += customer.getTotalCreditsBought();
            } else {
                creditSpent[4] += customer.getTotalCreditsBought();
            }
        }

        for (int i = 0; i < 5; i++) {
            percentageCreditSpent[i] = creditSpent[i] / totalCredits * 100;
        }

        System.out.println("Age Group - Below 20 (RM): " + String.format("%.2f", creditSpent[0]) + " (" + String.format("%.2f", percentageCreditSpent[0]) + "%)");
        System.out.println("Age Group - 20 - 29 (RM) : " + String.format("%.2f", creditSpent[1]) + " (" + String.format("%.2f", percentageCreditSpent[1]) + "%)");
        System.out.println("Age Group - 30 - 39 (RM) : " + String.format("%.2f", creditSpent[2]) + " (" + String.format("%.2f", percentageCreditSpent[2]) + "%)");
        System.out.println("Age Group - 40 - 49 (RM) : " + String.format("%.2f", creditSpent[3]) + " (" + String.format("%.2f", percentageCreditSpent[3]) + "%)");
        System.out.println("Age Group - Above 50 (RM): " + String.format("%.2f", creditSpent[4]) + " (" + String.format("%.2f", percentageCreditSpent[4]) + "%)");
        System.out.println();
    }

    public void mostPopularDayOfWeek(Schedule[] schedules) {
        System.out.println("\nThe Most Popular Day Of Week");
        System.out.println("============================\n");
        int totalTicketSold = 0;
        int[] ticketSoldPerDay = new int[7];
        double[] percentageTSPD = new double[7];

        for (Schedule schedule : schedules) {
            int availableTicket = schedule.getNumAvailable("Tickets");
            int maxTicket = schedule.getMaxTickets();
            int ticketSold = maxTicket - availableTicket;

            totalTicketSold += ticketSold;

            int dayOfWeek = schedule.getDate().getDayOfWeek().getValue();

            switch (dayOfWeek) {
                case 1 ->
                    ticketSoldPerDay[0] += ticketSold;
                case 2 ->
                    ticketSoldPerDay[1] += ticketSold;
                case 3 ->
                    ticketSoldPerDay[2] += ticketSold;
                case 4 ->
                    ticketSoldPerDay[3] += ticketSold;
                case 5 ->
                    ticketSoldPerDay[4] += ticketSold;
                case 6 ->
                    ticketSoldPerDay[5] += ticketSold;
                default ->
                    ticketSoldPerDay[6] += ticketSold;
            }
        }

        for (int i = 0; i < 7; i++) {
            percentageTSPD[i] = (double) ticketSoldPerDay[i] / totalTicketSold * 100;
        }

        System.out.println("Populalation on Monday   : " + String.format("%.2f", percentageTSPD[0]) + "% (" + ticketSoldPerDay[0] + ")");
        System.out.println("Populalation on Tuesday  : " + String.format("%.2f", percentageTSPD[1]) + "% (" + ticketSoldPerDay[1] + ")");
        System.out.println("Populalation on Wednesday: " + String.format("%.2f", percentageTSPD[2]) + "% (" + ticketSoldPerDay[2] + ")");
        System.out.println("Populalation on Thursday : " + String.format("%.2f", percentageTSPD[3]) + "% (" + ticketSoldPerDay[3] + ")");
        System.out.println("Populalation on Friday   : " + String.format("%.2f", percentageTSPD[4]) + "% (" + ticketSoldPerDay[4] + ")");
        System.out.println("Populalation on Saturday : " + String.format("%.2f", percentageTSPD[5]) + "% (" + ticketSoldPerDay[5] + ")");
        System.out.println("Populalation on Sunday   : " + String.format("%.2f", percentageTSPD[6]) + "% (" + ticketSoldPerDay[6] + ")");

    }

    public void mostPopularProductCategory(Schedule[] schedules, Vendor[] vendors) {
        System.out.println("\nThe Most Popular Product category");
        System.out.println("=================================\n");

        ArrayList<Transaction> allTransactions = new ArrayList();
        int totalOfTotalProduct;
        int[] totalProduct = new int[2];
        double[] percentage = new double[2];

        for (Schedule schedule : schedules) {
            ArrayList<Transaction> transactions = schedule.getTransaction();
            for (Transaction transaction : transactions) {
                allTransactions.add(transaction);
            }
        }

        ArrayList<Stall> allStalls = new ArrayList();
        for (Vendor vendor : vendors) {
            ArrayList<Stall> stalls = vendor.getStalls();
            for (Stall stall : stalls) {
                allStalls.add(stall);
            }
        }

        boolean isTransactionFound;

        for (Transaction transaction : allTransactions) {
            isTransactionFound = false;

            for (Stall stall : allStalls) {
                for (Product product : stall.getProducts()) {
                    if (transaction.getProductId().startsWith(product.getProductId()) && stall.getStallType() == StallType.FOOD) {
                        totalProduct[0] += transaction.getQuantity();
                        isTransactionFound = true;
                    } else if (transaction.getProductId().startsWith(product.getProductId()) && stall.getStallType() == StallType.GAME) {
                        totalProduct[1] += transaction.getQuantity();
                        isTransactionFound = true;
                    }

                    if (isTransactionFound) {
                        break;
                    }
                }

                if (isTransactionFound) {
                    break;
                }
            }
        }

        totalOfTotalProduct = totalProduct[0] + totalProduct[1];

        if (totalOfTotalProduct == 0) {
            System.out.println("FOOD Products Sold: " + totalProduct[0] + " (" + String.format("%.2f", 0.0) + "%)");
            System.out.println("GAME Products Sold: " + totalProduct[1] + " (" + String.format("%.2f", 0.0) + "%)");
        } else {
            for (int i = 0; i < 2; i++) {
                percentage[i] = (double) totalProduct[i] / totalOfTotalProduct * 100;
            }

            System.out.println("FOOD Products Sold: " + totalProduct[0] + " (" + String.format("%.2f", percentage[0]) + "%)");
            System.out.println("GAME Products Sold: " + totalProduct[1] + " (" + String.format("%.2f", percentage[1]) + "%)");
        }

    }

// Static Functionalities
    public static int setMaxNumOf(String type, int limit) {
        Scanner input = new Scanner(System.in);
        int maxNum;

        do {
            try {
                System.out.println("Enter maximum " + type + " to be opened for this schedule (Not more than " + limit + ")");
                System.out.print("Input number: ");

                maxNum = input.nextInt();
                input.nextLine();

                if (maxNum <= 0 || maxNum > limit) {
                    throw new InvalidChoiceException();
                } else {
                    return maxNum;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a valid number !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
