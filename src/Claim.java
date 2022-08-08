/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;


public class Claim extends Validations {
    PASDriver main = new PASDriver();
    Scanner input = new Scanner(System.in);
    private String claimNo, dateOfAccident, addressAccidentHappened, descriptionOfAccident, descriptionOfVehicleDamage;
    private double estimatedCostOfRepair;
    private boolean isClaimExist = true;
    LocalDate parseDateOfAccident, parseEffectiveDate, parseNewExpirationDate;
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);  // number format

    /**
     * Parameterized Constructor of Class Claim
     * @param claimNo string parameter for claim number
     * @param dateOfAccident string parameter for date of accident
     * @param addressAccidentHappened string parameter for address of accident
     * @param descriptionOfAccident string parameter for description of accident
     * @param descriptionOfVehicleDamage string parameter for description of vehicle damaged
     * @param estimatedCostOfRepair double parameter for estimated cost of repair of the vehicle damaged
     */
    public Claim(String claimNo, String dateOfAccident, String addressAccidentHappened, String descriptionOfAccident,
                 String descriptionOfVehicleDamage, double estimatedCostOfRepair) {
        this.claimNo = claimNo;
        this.dateOfAccident = dateOfAccident;
        this.addressAccidentHappened = addressAccidentHappened;
        this.descriptionOfAccident = descriptionOfAccident;
        this.descriptionOfVehicleDamage = descriptionOfVehicleDamage;
        this.estimatedCostOfRepair = estimatedCostOfRepair;
    }

    /**
     * Method for Filing An Accident Claim
     */
    public void load() {
        try {
            System.out.println();
            printBorderWithText(18, " File An Accident  Claim ");
            System.out.print(" Enter Policy Number                    ---> ");
            String policyNum = validateNumber("Policy Number", "PolicyNumber").trim();
            policy.selectPolicy(policyNum);
            checkStatus();
            if (policy.getIsPolicyExist()) {
                int generateNum = random.nextInt(999999);
                claimNo = String.format("C%06d", generateNum);
                System.out.print(" Enter Date of Accident(YYYY-MM-DD)     ---> ");
                this.dateOfAccident = validateAccidentDate().trim();
                System.out.print(" Enter Address of Accident              ---> ");
                this.addressAccidentHappened = validateAddress("AccidentClaim");
                System.out.print(" Enter Description of Accident          ---> ");
                this.descriptionOfAccident = validateAlpha("Description Of Accident","DescAccident");
                System.out.print(" Enter Description of Vehicle Damage    ---> ");
                this.descriptionOfVehicleDamage = validateAlpha("Description of Vehicle Damage",
                        "DescVehicleDamage");
                System.out.print(" Enter Estimated Cost of Repair         ---> ");
                this.estimatedCostOfRepair = nextValidCostOfRepair(input);
                System.out.println(" Claim Number                           ---> " + claimNo);
                printBorder(61);
                store(policyNum);
            } else {
                tryAgainClaim("Policy doesn't exist", "load");
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Method to store claim details on database
     * @param policyNo string parameter for policy number, stores its argument to the db
     */
    public void store(String policyNo) {
        try {
            connectOnDB();
            String query = "INSERT INTO claim (policy_no, claim_no, date_of_accident, address_of_accident," +
                    " desc_of_accident, desc_vehicle_damage, est_cost_repairs)" + "VALUES('" + policyNo + "'," +
                    " '" + claimNo + "', '" + dateOfAccident + "', '" + addressAccidentHappened + "', '" +
                    descriptionOfAccident + "', '" + descriptionOfVehicleDamage + "', '" + estimatedCostOfRepair + "')";
            prep = conn.prepareStatement(query);
            prep.execute();
            printSuccess("You have successfully filed an Accident Claim on Policy Number: " + policyNo);
            promptEnterKey();
            main.menuChoice();
        } catch (Exception e) {
            printError("Claim Create Failed!");
            main.menuChoice();
        }
    }

    /**
     * Method to search claim number on db
     */
    public void selectClaim() {
        try {
            connectOnDB();
            System.out.println();
            printBorderWithText(6," Search a Specific Claim ");
            System.out.print(" Enter Claim Number ---> ");
            String claimNum = validateNumber("Claim Number", "SearchClaimNumber").trim();
            printBorder(37);
            String query = "SELECT * FROM claim WHERE claim_no='" + claimNum + "'";
            result = stmt.executeQuery(query);
            if (!result.next()) isClaimExist = false;
            else {
                getClaimDetails(claimNum, result.getString("date_of_accident"),
                        result.getString("address_of_accident"),
                        result.getString("desc_of_accident"),
                        result.getString("desc_vehicle_damage"),
                        result.getDouble("est_cost_repairs")
                );
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Method to display claim details
     */
    public void display() {
        try {
            selectClaim();
            if (!isClaimExist) {
                printError("Claim doesn't exist");
            } else {
                System.out.println();
                printBorderWithText(47," Claim  Details ");
                System.out.format("%-15s %-15s %-20s %-23s %-20s %-20s%n", "ClaimNo", "DateOfAccident",
                        "AddressOfAccident", "DescriptionOfAccident", "VehicleDamage", "EstCostRepairs");
                printDashBorder(112);
                System.out.format("%-15s %-15s %-20s %-23s %-20s %-20s%n", claim.claimNo, claim.dateOfAccident,
                        claim.addressAccidentHappened, claim.descriptionOfAccident, claim.descriptionOfVehicleDamage,
                        currency.format(claim.estimatedCostOfRepair));
                printBorder(110);
                System.out.println();

            }
            promptEnterKey();
            main.menuChoice();
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Repeat the load method of Claim or the display method
     * @param message string parameter for message
     * @param purpose string instance for setting a condition to use in linking to a method
     */
    public void tryAgainClaim(String message, String purpose) {
        System.out.print("\n Error: " + message + "\n\nTry Again? (1)Yes (2)No ---> ");
        int opt = input.nextInt();
        input.nextLine();
        if (opt == 1 && purpose.equals("load")) {
            load();
        }
        if (opt == 1 && purpose.equals("display")) {
            display();
        } else {
            main.menuChoice();
        }
    }

    /**
     * Method to check the status of the policy
     */
    public void checkStatus() {
        if (policy.status.equals("Expired")) {
            printBorder(61);
            tryAgainClaim("Policy has been " + policy.status, "load");
        }

        if (policy.status.equals("Scheduled")) {
            printBorder(61);
            tryAgainClaim("Policy is still Inactive and " + policy.status + " to be effective on " +
                    "'" + policy.getEffectiveDate() + "'", "load");
        }
    }
}