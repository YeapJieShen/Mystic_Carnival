/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public abstract class User {

    private Account account;
    private String lastName;
    private String firstName;

// Constructors
    public User() {
    }

    public User(String username, String password, String lastName, String firstName) {
        this.account = new Account(username, password);
        this.lastName = lastName;
        this.firstName = firstName;
    }

// Getters
    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Account getAccount() {
        return this.account;
    }

// Abstract Methods
    public abstract void displayMenu(ArrayList<Schedule> schedulesList, Vendor[] vendors, Customer[] customers);

// Static Functionalities
    public static User login(ArrayList<User> users) {
        Scanner input = new Scanner(System.in);
        boolean usernameExists = false;
        boolean passwordMatched = false;

        System.out.println();
        System.out.println("User Login");
        System.out.println("==========");
        System.out.println();
        System.out.print("Enter username: ");

        String username = input.nextLine();
        User user = User.searchUser(users, username);

        if (user != null) {
            usernameExists = true;
            do {
                System.out.println();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                System.out.println();
                if (Account.verifyAccount(user.getAccount(), password)) {
                    passwordMatched = true;
                    System.out.println("Login Successful!");
                    System.out.println();
                } else {
                    System.out.println("Wrong password!");
                }
            } while (passwordMatched == false);
        }

        if (usernameExists == false) {
            System.out.println();
            System.out.println("Account does not exist! Please proceed to create account.");
        }
        return user;
    }

    public static User searchUser(ArrayList<User> users, String username) {
        for (User user : users) {
            if (user.account.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User createUser(ArrayList<User> users) {
        Scanner input = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList();

        for (User user : users) {
            accounts.add(user.getAccount());
        }

        System.out.println("\nCreate Account");
        System.out.println("==============\n");
        char createUser;

        do {
            try {
                System.out.print("Register as (C for customer or V for vendor): ");

                createUser = Character.toUpperCase(input.nextLine().charAt(0));

                switch (createUser) {
                    case 'C' -> {
                        return createCustomerAccount(accounts);

                    }
                    case 'V' -> {
                        return createVendorAccount(accounts);

                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid choice !");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

    }

    public static Customer createCustomerAccount(ArrayList<Account> accounts) {
        Scanner input = new Scanner(System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean isDuplicated, correctFormat, correctDateFormat;
        String newUsername, newPassword, lastName, firstName;
        char gender;
        LocalDate dob = null;

        do {
            try {
                System.out.println();
                System.out.print("Enter username (no restriction): ");

                newUsername = input.nextLine();
                isDuplicated = Account.checkDuplicateUsername(accounts, newUsername);

                if (isDuplicated) {
                    throw new DuplicatedNameException("Username has been used !!");

                } else if (newUsername.equals("")) {
                    throw new InvalidNameException("Please enter your username !!");
                } else {
                    break;
                }
            } catch (DuplicatedNameException | InvalidNameException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                newPassword = User.inputPassword();
                correctFormat = Account.validPasswordFormat(newPassword);

                if (!correctFormat) {
                    throw new InvalidChoiceException("Password does not fullfil required criteria !!");
                } else {
                    break;
                }
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.print("Enter your last name: ");
        lastName = input.nextLine();
        System.out.print("Enter your first name: ");
        firstName = input.nextLine();

        do {
            correctDateFormat = true;

            System.out.println();
            System.out.print("Enter your date of birth (yyyy-mm-dd): ");
            String date = input.nextLine();
            try {
                dob = LocalDate.parse(date, dateFormatter);
            } catch (DateTimeParseException e) {
                correctDateFormat = false;
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        } while (correctDateFormat == false);

        do {
            try {
                System.out.println();
                System.out.print("Enter your gender (F for female, M for male): ");

                gender = Character.toUpperCase(input.nextLine().charAt(0));

                switch (gender) {
                    case 'F' -> {
                        Customer customer = new Customer(newUsername, newPassword, lastName, firstName, dob, Gender.FEMALE);
                        return customer;
                    }
                    case 'M' -> {
                        Customer customer = new Customer(newUsername, newPassword, lastName, firstName, dob, Gender.MALE);
                        return customer;
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter your choice !!");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static Vendor createVendorAccount(ArrayList<Account> accounts) {
        Scanner input = new Scanner(System.in);
        boolean isDuplicated, correctFormat;
        String newUsername, newPassword, lastName, firstName;

        do {
            try {
                System.out.println();
                System.out.print("Enter username (no restriction): ");

                newUsername = input.nextLine();
                isDuplicated = Account.checkDuplicateUsername(accounts, newUsername);

                if (isDuplicated) {
                    throw new DuplicatedNameException("Username has been used !!");

                } else if (newUsername.equals("")) {
                    throw new InvalidNameException("Please enter your username !!");
                } else {
                    break;
                }
            } catch (DuplicatedNameException | InvalidNameException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                newPassword = User.inputPassword();
                correctFormat = Account.validPasswordFormat(newPassword);

                if (!correctFormat) {
                    throw new InvalidChoiceException("Password does not fullfil required criteria !!");
                } else {
                    break;
                }
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.print("Enter your last name: ");
        lastName = input.nextLine();
        System.out.print("Enter your first name: ");
        firstName = input.nextLine();

        return new Vendor(newUsername, newPassword, lastName, firstName);
    }

    public static String inputPassword() {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Your password must: ");
        System.out.println("Must contain at least 8 characters ");
        System.out.println("At least one uppercase letter");
        System.out.println("At least one lowercase letter");
        System.out.println("At least one number");
        System.out.println("At least one special character (@$!%*?&)");
        System.out.println();
        System.out.print("Enter your password: ");
        return input.nextLine();
    }
}
