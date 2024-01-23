/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public class SomeMain {

    public static void main(String[] args) {
        boolean isTerminate = false;
        ArrayList<Schedule> schedules = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        addUsers(users);
        addSchedules(schedules);

        do {
            isTerminate = loginMenu(users, schedules);
        } while (!isTerminate);

    }

    public static boolean loginMenu(ArrayList<User> users, ArrayList<Schedule> schedules) {
        Scanner input = new Scanner(System.in);
        char option;

        ArrayList<Vendor> vendorsList = new ArrayList();
        ArrayList<Customer> customersList = new ArrayList();

        for (User user : users) {
            if (user instanceof Vendor vendor) {
                vendorsList.add(vendor);
            } else if (user instanceof Customer customer) {
                customersList.add(customer);
            }
        }

        Vendor[] vendors = new Vendor[vendorsList.size()];
        vendorsList.toArray(vendors);
        Customer[] customers = new Customer[customersList.size()];
        customersList.toArray(customers);

        System.out.println();
        System.out.println("Welcome to Carnival!");
        System.out.println();
        System.out.println("====================");

        do {
            try {
                System.out.println();
                System.out.println("Do you have an account?");
                System.out.println("[Y] Yes");
                System.out.println("[N] No");
                System.out.println();
                System.out.println("Press [E] to EXIT the program");
                System.out.print("Your Choice: ");

                option = Character.toUpperCase(input.nextLine().charAt(0));

                switch (option) {
                    case 'Y' -> {
                        User user = User.login(users);
                        if (user == null) {
                            return false;
                        } else {
                            if (user instanceof Vendor vendor) {
                                vendor.vendorPage(schedules);
                            } else if (user instanceof Customer customer) {
                                customer.displayMenu(schedules, vendors, customers);
                            } else if (user instanceof Admin admin) {
                                admin.displayMenu(schedules, vendors, customers);
                            }
                            return false;
                        }
                    }

                    case 'N' -> {
                        User user = User.createUser(users);
                        users.add(user);
                        System.out.println("Account successfully created! Please proceed to login.\n");
                        System.out.println(user.getAccount());
                        return false;
                    }
                    case 'E' -> {
                        return true;
                    }
                    default -> {
                        throw new InvalidChoiceException("Please enter your choice !!");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter your choice !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static void addUsers(ArrayList<User> usersList) {
        usersList.add(Admin.getInstance());
        addVendors(usersList);
        addCustomers(usersList);
    }

    public static void addSchedules(ArrayList<Schedule> schedulesList) {
        schedulesList.add(new Schedule(LocalDate.of(2023, 8, 31), LocalTime.of(8, 0, 0), LocalTime.of(12, 0, 0), 20, 5, 10, 5));
        schedulesList.add(new Schedule(LocalDate.of(2023, 8, 30), LocalTime.of(17, 0, 0), LocalTime.of(22, 0, 0), 40, 10, 20, 10));
        schedulesList.add(new Schedule(LocalDate.of(2023, 9, 7), LocalTime.of(17, 0, 0), LocalTime.of(23, 0, 0), 40, 10, 20, 10));
        schedulesList.add(new Schedule(LocalDate.of(2023, 9, 8), LocalTime.of(7, 0, 0), LocalTime.of(12, 0, 0), 15, 5, 10, 7));
        schedulesList.add(new Schedule(LocalDate.of(2023, 9, 9), LocalTime.of(18, 0, 0), LocalTime.of(23, 0, 0), 50, 10, 20, 10));
        schedulesList.add(new Schedule(LocalDate.of(2023, 9, 10), LocalTime.of(9, 0, 0), LocalTime.of(12, 0, 0), 30, 10, 15, 8));
    }

    public static void addVendors(ArrayList<User> usersList) {
        usersList.add(new Vendor("John76", "JohnIs76!", "Smith", "John"));
        usersList.add(new Vendor("Doe88", "MeisDoe8?", "Doe", "John"));
        usersList.add(new Vendor("Green10", "Green10Green!", "Green", "Danny"));
        usersList.add(new Vendor("White18", "18@White", "White", "Kelly"));
        usersList.add(new Vendor("King99", "imKing99!", "King", "Wilson"));

        Vendor tempVendor = (Vendor) usersList.get(1);
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "YumYum", StallType.FOOD));
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "Yummie", StallType.FOOD));

        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Nasi Lemak", 1.99, 100, "Sangat sedap !!"));
        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Nasi Kuning", 3.99, 50, "Lebih sedap !!"));
        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Nasi Cendawan", 6.99, 20, "Tersedap !!"));

        Stall tempStall = tempVendor.getStalls().get(0);
        Product tempProduct = tempVendor.getProducts().get(0);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempStall = tempVendor.getStalls().get(0);
        tempProduct = tempVendor.getProducts().get(1);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempStall = tempVendor.getStalls().get(1);
        tempProduct = tempVendor.getProducts().get(2);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempVendor = (Vendor) usersList.get(2);
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "DoeYum", StallType.FOOD));
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "DoeGame", StallType.GAME));

        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Mee Mee", 3.99, 70, "Very sedap !!"));
        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Dart Game", 6, 20, "Sangat bahaya !!"));

        tempStall = tempVendor.getStalls().get(0);
        tempProduct = tempVendor.getProducts().get(0);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempStall = tempVendor.getStalls().get(1);
        tempProduct = tempVendor.getProducts().get(1);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempVendor = (Vendor) usersList.get(3);
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "GreenGame", StallType.GAME));
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "GreenFood", StallType.FOOD));

        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Balloon Popping", 8, 20, "Very fun!!"));
        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Ayam Bakor", 9.99, 10, "Very ayam !!"));

        tempStall = tempVendor.getStalls().get(0);
        tempProduct = tempVendor.getProducts().get(0);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempStall = tempVendor.getStalls().get(1);
        tempProduct = tempVendor.getProducts().get(1);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempVendor = (Vendor) usersList.get(4);
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "WhiteMie", StallType.FOOD));

        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Corny corn", 3, 20, "Sangat butter!!"));

        tempStall = tempVendor.getStalls().get(0);
        tempProduct = tempVendor.getProducts().get(0);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

        tempVendor = (Vendor) usersList.get(5);
        tempVendor.getStalls().add(new Stall(tempVendor.getAccount().getUsername(), "KingGame", StallType.GAME));

        tempVendor.getProducts().add(new Product(tempVendor.getAccount().getUsername(), "Fishing", 4, 20, "Very fishy!!"));

        tempStall = tempVendor.getStalls().get(0);
        tempProduct = tempVendor.getProducts().get(0);

        tempProduct.updateProductId(tempStall.getStallId());
        tempStall.getProducts().add(tempProduct);

    }

    public static void addCustomers(ArrayList<User> usersList) {
        usersList.add(new Customer("cust1", "Imcust1!", "Gan", "Jing", LocalDate.of(2002, 11, 8), Gender.FEMALE));
        usersList.add(new Customer("cust2", "Imcust2!", "Gan", "Jing", LocalDate.of(2000, 12, 12), Gender.MALE));
        usersList.add(new Customer("cust3", "Iamcust3!", "Gan", "Jing", LocalDate.of(2003, 1, 6), Gender.FEMALE));
        usersList.add(new Customer("cust4", "Meiscust4$", "Gan", "Jing", LocalDate.of(1998, 8, 9), Gender.MALE));
        usersList.add(new Customer("cust5", "Me@cust5", "Gan", "Jing", LocalDate.of(1999, 6, 23), Gender.FEMALE));
        usersList.add(new Customer("cust6", "Immacust6*", "Gan", "Jing", LocalDate.of(1987, 10, 19), Gender.MALE));
        usersList.add(new Customer("cust7", "Amicust7?", "Gan", "Jing", LocalDate.of(2003, 4, 16), Gender.FEMALE));
        usersList.add(new Customer("cust8", "&Mecust8", "Gan", "Jing", LocalDate.of(2005, 5, 21), Gender.MALE));
    }
}

class InvalidScheduleTimeException extends RuntimeException {

    public InvalidScheduleTimeException(String message) {
        super(message);
    }
}

class DuplicatedNameException extends RuntimeException {

    public DuplicatedNameException(String message) {
        super(message);
    }
}

class InvalidNameException extends RuntimeException {

    public InvalidNameException(String message) {
        super(message);
    }
}

class InvalidChoiceException extends RuntimeException {

    public InvalidChoiceException() {
        this("\nInvalid Choice !!\nPlease enter a valid choice !!");
    }

    public InvalidChoiceException(String message) {
        super(message);
    }
}

class TicketInvalidException extends RuntimeException {

    public TicketInvalidException(String message) {
        super(message);
    }
}

class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}

class InsufficientCreditException extends RuntimeException {

    public InsufficientCreditException(String message) {
        super(message);
    }
}
