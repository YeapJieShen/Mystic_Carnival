/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Jie Shen
 */
public class Vendor extends User {

    private final ArrayList<Stall> STALLS = new ArrayList<>();
    private final ArrayList<Product> PRODUCTS = new ArrayList<>();

// Constructors
    public Vendor() {
        this("unknown", "unknown", "unknown", "unknown");
    }

    public Vendor(String username, String password, String lastName, String firstName) {
        super(username, password, lastName, firstName);
    }

    public ArrayList<Stall> getStalls() {
        return STALLS;
    }

    public ArrayList<Product> getProducts() {
        return PRODUCTS;
    }

    //create stalls
    public void createStall() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        System.out.println("\nCreate Stall");
        System.out.println("============\n");
        System.out.print("Enter stall name: ");
        String stallName = input.nextLine();
        do {
            try {
                System.out.print("Enter stall type (F for food, G for game): ");
                String stallType = input.nextLine().toUpperCase();
                switch (stallType.charAt(0)) {
                    case 'F' -> {
                        Stall stall = new Stall(this.getAccount().getUsername(), stallName, StallType.FOOD);
                        STALLS.add(stall);
                        System.out.println("New stall successfully created!");
                        System.out.println(stall.toString());
                        successful = true;
                    }
                    case 'G' -> {
                        Stall stall = new Stall(this.getAccount().getUsername(), stallName, StallType.GAME);
                        STALLS.add(stall);
                        System.out.println("New stall successfully created!");
                        System.out.println(stall.toString());
                        successful = true;
                    }
                    default -> {
                        successful = false;
                        throw new InvalidChoiceException();
                    }
                }
            } catch (java.util.InputMismatchException | java.lang.StringIndexOutOfBoundsException e) {
                System.out.println("Invalid input. Please enter a valid character.");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (successful == false);
    }

    //edit stall name
    public void editStallName() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        System.out.println("\nEdit Stall Name");
        System.out.println("===============\n");
        do {
            System.out.print("Enter stall ID: ");
            String stallId = input.nextLine();
            for (Stall stall : STALLS) {
                if (stall.getStallId().equals(stallId)) {
                    System.out.print("Enter new stall name: ");
                    String stallName = input.nextLine();
                    stall.setStallName(stallName);
                    System.out.println("Stall name successfully updated!");
                    System.out.println(stall.toString());
                    successful = true;
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Stall ID does not exists!");
            }
        } while (successful == false);
    }

    //edit stall type
    public void editStallType() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        boolean correctOption;
        System.out.println("\nEdit Stall Type");
        System.out.println("===============\n");
        do {
            System.out.print("Enter stall ID: ");
            String stallId = input.nextLine();
            for (Stall stall : STALLS) {
                if (stall.getStallId().equals(stallId)) {
                    successful = true;
                    do {
                        System.out.print("Enter stall type (F for food, G for game): ");
                        String stallType = input.nextLine().toUpperCase();
                        if (stallType.charAt(0) != 'F' && stallType.charAt(0) != 'G') {
                            correctOption = false;
                            System.out.println("Update failed. Invalid stall type!\n");
                        } else {
                            correctOption = true;
                            if (stallType.charAt(0) == 'F') {
                                stall.setStallType(StallType.FOOD);
                                System.out.println("New stall type successfully updated!");
                                System.out.println(stall.toString());
                            } else {
                                stall.setStallType(StallType.GAME);
                                System.out.println("New stall type successfully updated!");
                                System.out.println(stall.toString());
                            }
                        }
                    } while (correctOption == false);
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Stall ID does not exists!");
            }
        } while (successful == false);
    }

    //create product
    public void addProduct() {
        Scanner input = new Scanner(System.in);
        double unitPrice = 0;
        int stock = 0;

        System.out.println("\nAdd Product");
        System.out.println("===========\n");
        System.out.print("Enter product name: ");
        String productName = input.nextLine();

        do {
            try {
                System.out.print("Enter product unit price (RM): ");

                unitPrice = input.nextDouble();
                input.nextLine();

                if (unitPrice <= 0) {
                    throw new InvalidChoiceException();
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println();
                System.out.println("Invalid input format!");
                System.out.println();
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Enter product stock: ");
                stock = input.nextInt();
                input.nextLine();

                if (stock < 0) {
                    throw new InvalidChoiceException();
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println();
                System.out.println("Invalid input format!");
                System.out.println();
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.print("Enter product description: ");
        String description = input.nextLine();
        Product product = new Product(this.getAccount().getUsername(), productName, unitPrice, stock, description);
        PRODUCTS.add(product);
        System.out.println();
        System.out.println("Product successfully added!");
        System.out.println(product.toString());
    }

    //edit product name
    public void editProductName() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        System.out.println("\nEdit Product Name");
        System.out.println("=================\n");
        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();
            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    System.out.print("Enter new product name: ");
                    String productName = input.nextLine();
                    product.setProductName(productName);
                    System.out.println("Product name successfully updated!");
                    System.out.println(product.toString());
                    successful = true;
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);
    }

    //edit product description
    public void editProductDescription() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;

        System.out.println("\nEdit Product Description");
        System.out.println("========================\n");

        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();

            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    System.out.print("Enter new product description: ");
                    String desc = input.nextLine();
                    product.setProductDescription(desc);
                    System.out.println("Product description successfully updated!");
                    System.out.println(product.toString());
                    successful = true;
                    break;
                }
            }

            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);
    }

    //edit product unit price
    public void editProductUnitPrice() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        double unitPrice = 0;
        System.out.println("\nEdit Product Price");
        System.out.println("==================\n");
        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();
            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    do {
                        try {
                            successful = true;
                            System.out.print("Enter new product unit price (RM): ");
                            unitPrice = input.nextDouble();
                        } catch (java.util.InputMismatchException e) {
                            successful = false;
                            System.out.println("Invalid input format!\n");
                        }
                    } while (successful == false);
                    product.setUnitPrice(unitPrice);
                    System.out.println("Product unit price successfully updated!");
                    System.out.println(product.toString());
                    successful = true;
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);
    }

    //edit product stock
    public void editProductStock() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        int stock = 0;
        System.out.println("\nEdit Product Stock");
        System.out.println("==================\n");
        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();
            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    do {
                        try {
                            successful = true;
                            System.out.print("Enter new product stock: ");
                            stock = input.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            successful = false;
                            System.out.println("Invalid input format!\n");
                        }
                    } while (successful == false);
                    product.setStock(stock);
                    System.out.println("Product stock successfully updated!");
                    System.out.println(product.toString());
                    successful = true;
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);
    }

    //remove product
    public void removeProduct() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;
        System.out.println("\nRemove Product");
        System.out.println("==============\n");
        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();
            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    PRODUCTS.remove(product);
                    System.out.println("Product successfully removed!");
                    successful = true;
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);
    }

    //assign product to a stall
    public void assignProduct() {
        Scanner input = new Scanner(System.in);
        boolean successful = false;

        System.out.println();
        System.out.println("Assign Product To Stall");
        System.out.println("=======================");
        System.out.println();

        do {
            System.out.print("Enter product ID: ");
            String productId = input.nextLine();
            for (Product product : PRODUCTS) {
                if (product.getProductId().equals(productId)) {
                    do {
                        System.out.print("Enter Stall Id: ");
                        String stallId = input.nextLine();
                        for (Stall stall : STALLS) {
                            if (stall.getStallId().equals(stallId)) {
                                successful = true;
                                product.updateProductId(stallId);
                                stall.addProduct(product);
                                System.out.println("Product successfully assigned to the stall!");
                                System.out.println();
                                System.out.println(stall.toString());
                                break;
                            }
                        }
                        if (successful == false) {
                            System.out.println("Stall Id does not exits!\n");
                        }
                    } while (successful == false);
                    break;
                }
            }
            if (successful == false) {
                System.out.println("Product ID does not exists!");
            }
        } while (successful == false);

    }

    public void searchStallToRentSlot(ArrayList<Schedule> schedules) {
        Scanner input = new Scanner(System.in);
        boolean stallExists = false;
        boolean scheduleExists = false;
        boolean slotAvailable = true;
        System.out.println("\nRent Slot");
        System.out.println("==========\n");
        do {
            System.out.print("Please enter stall ID: ");
            String stallId = input.nextLine();
            for (Stall stall : this.getStalls()) {
                if (stall.getStallId().equals(stallId)) {
                    stallExists = true;
                    do {
                        try {
                            System.out.println("RENT SLOT");
                            System.out.println();
                            System.out.println(String.format("%-12s%-12s%11s%11s", "Shcedule ID", "Date", "Start Time", "End Time"));
                            for (Schedule schedule : schedules) {
                                System.out.println(String.format("%-12s%32s",
                                        String.format("%d", schedule.getId()),
                                        schedule.toString()));
                            }
                            System.out.print("Please enter schedule ID: ");
                            int scheduleId = input.nextInt();
                            input.nextLine();
                            for (Schedule schedule : schedules) {
                                if (schedule != null && schedule.getId() == scheduleId) {
                                    scheduleExists = true;
                                    for (Slot slot : stall.getSlots()) {
                                        if (slot.getSlotId().startsWith(String.format("%d", scheduleId))) {
                                            System.out.println();
                                            System.out.println("Stall has bought slot for this schedule !!");
                                            return;
                                        }
                                    }
                                    for (Slot slot : schedule.getSlots()) {
                                        if (slot.getSlotStatus() == Status.AVAILABLE) {
                                            slotAvailable = true;
                                            slot.setSlotStatus(Status.USED);
                                            stall.rentSlot(slot);
                                            System.out.println("Slot successfully rented!\n");
                                            System.out.println(stall.toString());
                                            break;
                                        }
                                    }
                                    if (slotAvailable == false) {
                                        System.out.println("No slot available!");
                                    }
                                    break;
                                }
                            }
                            if (scheduleExists == false) {
                                System.out.println("Invalid schedule Id!\n");
                            }
                        } catch (InputMismatchException e) {
                            input.nextLine();
                        }
                    } while (scheduleExists == false);
                    break;
                }
            }
            if (stallExists == false) {
                System.out.println("Stall Id does not exit!");
            }
        } while (stallExists == false);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stalls:").append("\n");
        for (Stall stall : STALLS) {
            sb.append(stall.toString()).append("\n");
        }
        sb.append("\n\n Unassigned Products:");
        for (Product product : PRODUCTS) {
            if (!product.getProductId().matches("\\w*P\\d+ST\\d+")) {
                sb.append(product.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void displayMenu(ArrayList<Schedule> schedulesList, Vendor[] vendors, Customer[] customers) {
        displayMenu();
    }

    public int displayMenu() {
        //Welcome vendor

        Scanner input = new Scanner(System.in);
        int option;

        System.out.println();
        System.out.println("VENDOR MENU");
        System.out.println("===========");
        System.out.println();
        System.out.println("Hello, " + getLastName() + " !");
        System.out.println();

        do {
            try {
                System.out.println("Please select one of the task to perform (1 or 2 or 3):");
                System.out.println("1. Manage Stall");
                System.out.println("2. Manage Product");
                System.out.println("3. Check Inventory");
                System.out.println();
                System.out.println("Press [0] to LOGOUT");
                System.out.print("Your choice: ");
                option = input.nextInt();
                input.nextLine(); // Clear the input buffer
                switch (option) {
                    case 0 -> {
                        return option;
                    }
                    case 1 -> {
                        do {
                            try {
                                //Get option
                                System.out.println("\nPlease select one of the task to perform (11 or 12 or 13):");
                                System.out.println("11. Create Stall");
                                System.out.println("12. Edit Stall Name");
                                System.out.println("13. Edit Stall Type");
                                System.out.println("14. Rent Slot");
                                System.out.println();
                                System.out.println("Press [0] to BACK");
                                System.out.print("Your Choice: ");
                                option = input.nextInt();
                                input.nextLine(); // Clear the input buffer

                                switch (option) {
                                    case 11, 12, 13, 14 -> {
                                        return option;
                                    }
                                    case 0 -> {
                                        return -1;
                                    }
                                    default ->
                                        throw new InvalidChoiceException();
                                }

                            } catch (InputMismatchException e) {
                                input.nextLine();
                                System.out.println("Invalid input. Please enter a valid number.");
                            } catch (InvalidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (true);
                    }

                    case 2 -> {
                        do {
                            try {
                                //Get option
                                System.out.println("\nPlease select one of the task to perform (21 - 27):");
                                System.out.println("21. Add Product");
                                System.out.println("22. Edit Product Name");
                                System.out.println("23. Edit Product Description");
                                System.out.println("24. Edit Product Unit Price");
                                System.out.println("25. Edit Product Stock");
                                System.out.println("26. Remove Product");
                                System.out.println("27. Assign Product to Stall");
                                System.out.println();
                                System.out.println("Press [0] to BACK");
                                System.out.print("Your choice: ");
                                option = input.nextInt();
                                input.nextLine(); // Clear the input buffer

                                switch (option) {
                                    case 21, 22, 23, 24, 25, 26, 27 -> {
                                        return option;
                                    }
                                    case 0 -> {
                                        return -1;
                                    }
                                    default ->
                                        throw new InvalidChoiceException();
                                }
                            } catch (InputMismatchException e) {
                                input.nextLine();
                                System.out.println("Invalid input. Please enter a valid number.");
                                input.nextLine(); // Clear the input buffer
                            } catch (InvalidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (true);
                    }
                    case 3 -> {
                        return option;
                    }
                    default -> {
                        throw new InvalidChoiceException();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");

            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void vendorPage(ArrayList<Schedule> schedules) {
        do {
            int vendorOption = displayMenu();

            switch (vendorOption) {
                case -1 -> {
                    continue;
                }
                case 0 -> {
                    return;
                }
                case 3 ->
                    System.out.println("\n" + this.toString());

                case 11 ->
                    createStall();

                case 12 ->
                    editStallName();

                case 13 ->
                    editStallType();

                case 14 ->
                    searchStallToRentSlot(schedules);

                case 21 ->
                    addProduct();

                case 22 ->
                    editProductName();

                case 23 ->
                    editProductDescription();

                case 24 ->
                    editProductUnitPrice();

                case 25 ->
                    editProductStock();

                case 26 ->
                    removeProduct();

                case 27 ->
                    assignProduct();
            }
        } while (true);
    }
// Static Functionalities
}
