/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/*
 *Vehicle Class Inherits the attributes and methods from the PolicyHolder Class
 */
public class Vehicle extends PolicyHolder {
    PASDriver main = new PASDriver();
    Scanner input = new Scanner(System.in);
    RatingEngine rate = new RatingEngine();
    Policy policy = new Policy("", "", "", "", 0);

    public String make, model, type, fuelType, uuid, color;
    public int year;
    public double purchasePrice, premiumCharged, totalCost;
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
    private final ArrayList<Vehicle> list = new ArrayList<>();


    /**
     * Parameterized Constructor of Class CustomerAccount
     * @param make string parameter for vehicle's brand / make
     * @param model string parameter for vehicle's model
     * @param type string parameter for type of vehicle
     * @param fuelType string parameter for vehicle's fuel
     * @param uuid string parameter for vehicle's universally unique identifier (uuid)
     * @param year int parameter for vehicle's year of purchase
     * @param purchasePrice double parameter for vehicle's purchase price
     * @param premiumCharged double parameter for vehicle's premium charge/cost
     * @param color string parameter for vehicle's color
     */
    public Vehicle(String make, String model, String type, String fuelType, String uuid,
                   int year, double purchasePrice, double premiumCharged, String color) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.uuid = uuid;
        this.year = year;
        this.purchasePrice = purchasePrice;
        this.premiumCharged = premiumCharged;
        this.color = color;
    }


    /**
     * Method for vehicle details
     * @param dlx double parameter for years in between the driver license issued date and today's date from PolicyHolder Class
     * @param accountNo string parameter of customers account number
     * @param policyHolderUuid string parameter for policyholder's
     * @param policyNo string parameter for policy number
     * @param firstNamePolHolder string parameter for policyholder's first name
     * @param lastNamePolHolder string parameter for policyholder's last name
     * @param accountType string parameter for policyholder's relationship with the customer account
     * @param dateOfBirth string parameter for policyholder's date of birth
     * @param address string parameter for policyholder's address
     * @param driversLicenseNum string parameter for policyholder's driver's license number
     * @param driversLicenseIssued string parameter for policyholder's drivers license date issued
     */
    public void load(double dlx, String accountNo, String policyHolderUuid, String policyNo, String firstNamePolHolder,
                     String lastNamePolHolder, String accountType, LocalDate dateOfBirth, String address,
                     String driversLicenseNum, LocalDate driversLicenseIssued) {
        try {
            printBorderWithText(19," Vehicle Details ");
            System.out.print(" How many vehicles do you want to insured ---> ");

            int quantity = nextValidInt(input);
            int x = 1;

            while (x <= quantity) {
                System.out.println("\n                   +~~ Vehicle " + x + " ~~+              ");
                System.out.print(" Vehicle's Make                           ---> ");
                make = validateAlpha("Make","Alpha").toUpperCase();
                System.out.print(" Vehicle's Model                          ---> ");
                model = validateNumber("Model", "AlphaNumSpace").toUpperCase();
                System.out.print(" Vehicle's Purchase Year                  ---> ");
                year = nextValidYear(input);
                validationVehiclePurchaseYear();
                System.out.print(" Vehicle's Type                           ---> ");
                input.nextLine();
                type = validateAlpha("Type","Alpha").toUpperCase();
                System.out.print(" Vehicle's Fuel Type                      ---> ");
                fuelType = validateAlpha("Fuel","Alpha").toUpperCase();
                System.out.print(" Vehicle's Color                          ---> ");
                color = validateAlpha("Color", "Alpha").toUpperCase();
                System.out.print(" Vehicle's Purchase Price                 ---> ");
                purchasePrice = nextValidPrice(input);
                input.nextLine();
                premiumCharged = rate.calculatePremium(purchasePrice, year, dlx);
                System.out.println(" Premium Charged                          ---> "+ currency.format(premiumCharged));
                printBorder(55);
                totalCost = totalCost + premiumCharged;
                uuid = getUUID(); // generate
                list.add(new Vehicle(make, model, type, fuelType, uuid, year, purchasePrice, premiumCharged, color));
                if (quantity == x) {
                    display(accountNo, policyNo, policyHolderUuid, firstNamePolHolder, lastNamePolHolder, accountType,
                            dateOfBirth, address, driversLicenseNum, driversLicenseIssued);
                }
                x++;
            }
        } catch (Exception e) {
            printError(e.getMessage());
            main.menuChoice();
        }
    }

    /**
     * Method for displaying summary: policyholder details and vehicle details
     */
    public void display(String accountNo, String policyNo, String policyHolderUuid, String firstNamePolHolder,
                        String lastNamePolHolder, String accountType, LocalDate dateOfBirth, String address,
                        String driversLicenseNum, LocalDate driversLicenseIssued) {
        System.out.println("\n\n                                                 ~~~ S U M M A R Y ~~~                                               ");
        printBorderWithText(48," Policy Holder Details ");
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", "PolicyNo", "FirstName", "LastName",
                "Relationship", "DateOfBirth", "Address", "DriversLicenseNo", "DriversLicenseDate");
        printDashBorder(121);
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-16s %-30s%n", policyNo, firstNamePolHolder,
                lastNamePolHolder, accountType, dateOfBirth, address, driversLicenseNum, driversLicenseIssued);
        printBorder(119);
        System.out.println();
        printBorderWithText(51," Vehicle Details ");
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", "Make", "Model", "Type", "FuelType",
                "Year", "Color", "PurchasePrice", "PremiumCharged");
        printDashBorder(121);
        for (Vehicle vehicle : list) {
            System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", vehicle.make, vehicle.model,
                    vehicle.type, vehicle.fuelType, vehicle.year, vehicle.color, currency.format(vehicle.purchasePrice),
                    currency.format(vehicle.premiumCharged));
        }
        printBorder(119);
        System.out.println("                                    ~~~  Total Policy Premium Charged ---> "
                                     + currency.format(Double.valueOf(totalCost)) + "  ~~~                    ");
        buyPolicy(accountNo, policyNo, policyHolderUuid);
    }

    /**
     *Method for buying the policy
     */
    public void buyPolicy(String accountNo, String policyNo, String policyHolderUuid) {
        try {
            System.out.print("\n\nDo you want to buy this Policy? (1)Yes (2)No ---> ");
            int opt = input.nextInt();
            if (opt == 1) {
                store(accountNo, policyNo, policyHolderUuid);
                promptEnterKey();
                main.menuChoice();
            }
            if (opt == 2) {
                deleteFromDb("policy", "policy_no", policyNo);
                deleteFromDb("policy_holder", "uuid", policyHolderUuid);
                printSuccess("Policy not sold!");
                promptEnterKey();
                main.menuChoice();
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }


    /**
     * Method for storing vehicle details on db
     */
    public void store(String accountNo, String policyNo, String policyHolderUuid) {
        for (Vehicle vehicle : list) {
            String fields = "uuid, customer_acc_no, policy_holder_uuid, policy_no, make, model, year, type, fuel_type" +
                    ", color, purchase_price, premium_charged";
            String values = "'" + vehicle.uuid + "', '" + accountNo + "', '" + policyHolderUuid + "', '" + policyNo +
                    "', '" + vehicle.make + "', '" + vehicle.model + "', '" + vehicle.year + "', '" + vehicle.type +
                    "', '" + vehicle.fuelType + "', '" + vehicle.color + "', '" + vehicle.purchasePrice + "', '" +
                    vehicle.premiumCharged + "'";
            storeOnDB("vehicle", fields, values, "");
        }
        policy.updateOnDB("policy", "cost", String.valueOf(totalCost), "policy_no", policyNo);
        printSuccess("Policy Sold!");
    }

    /**
     * Method for vehicle display used in policy class
     */
    public void vehicleDisplay() {
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", this.make, this.model,
                this.type, this.fuelType, this.year, this.color,currency.format(this.purchasePrice),
                currency.format(this.premiumCharged));
        printBorder(119);
    }

    /**
     * Validation for the year purchase of the vehicle
     */
    public void validationVehiclePurchaseYear(){
            int today = LocalDate.now().getYear();
            int years = today - year;

            if (years > 40){
                printBorder(55);
                System.out.println("\n Error: Vehicle age must not exceed to 40 years!");
                System.out.println("        Please input a valid year (1982-present)\n");
                printBorder(55);
                System.out.print(" Vehicle's Purchase Year                  ---> ");
                year = input.nextInt();
           }
    }

}
