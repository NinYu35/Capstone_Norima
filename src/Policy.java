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

public class Policy extends Validations {
    PASDriver main = new PASDriver();
    Scanner input = new Scanner(System.in);
    public String firstName, lastName, dateOfBirth, address, driversLicenseNum, dateIssued, policyNo, status, policyNum,
            accType, effectiveDate, newExpiryDate, totalPremiumCharged;
    private final String expirationDate;
    private boolean isPolicyExist = true;
    public final double cost;
    LocalDate parseNewExpDate, parseEffDate, effectDate, expireDate, sixMonthsPolicyTerm;

    /**
     * Parameterized Constructor of Class CustomerAccount
     * @param policyNo string parameter for policy number
     * @param effectiveDate string parameter for effective date
     * @param expirationDate string parameter for expiration date
     * @param status string parameter for policy's status
     * @param cost string parameter for policy's total premium charged
     */
    public Policy(String policyNo, String effectiveDate, String expirationDate, String status, double cost) {
        this.policyNo = policyNo;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.cost = cost;
    }

    /**
     * Create a policy quote and buy policy
     */
    public void load() {
        try {
            policyHolder = new PolicyHolder();
            vehicle = new Vehicle("", "", "", "", "", 0, 0.0,
                    0.0, "");
            printBorderWithText(7," Input A Policy Quote And Buy The Policy ");
            account.checkAccountIfExist();
            if (!account.isAccountExist()) {
                tryAgain("Account doesn't exist!", "load");
            } else {
                int generateNum = random.nextInt(999999);
                policyNo = String.format("%06d", generateNum);
                System.out.print(" Enter Effective Date (YYYY-MM-DD)        ---> ");
                this.effectiveDate = validateEffectiveDate().trim();
                expireDate = effectDate.plusMonths(6);
                System.out.println(" Expiration Date                          ---> " + expireDate);
                System.out.println(" Policy Number                            ---> " + policyNo);
                printBorder(55);
                setPolicyStatus();
                store(effectDate, expireDate);
                policyHolder.load(policy.getPolicyNo());
                vehicle.load(policyHolder.getDlx(), account.getAccountNo(), policyHolder.getOwnUUID(),
                        policy.getPolicyNo(), policyHolder.getFirstNamePolicyHolder(),
                        policyHolder.getLastNamePolicyHolder(), policyHolder.getAccType(),
                        policyHolder.getDateOfBirth(), policyHolder.getAddress(),
                        policyHolder.getDriversLicenseNum(), policyHolder.getDriversLicenseIssued());
            }

        } catch (Exception e) {
            tryAgain("Invalid Format!", "load");
        }
    }


    /**
     * Stores policy details on db
     * @param effDate stores effective date to db
     * @param expireDate stores expiration date to db
     */
    public void store(LocalDate effDate, LocalDate expireDate) {
        String fields = "policy_no, customer_acc_no, effective_date, expiry_date, status";
        String values = "'" + policyNo + "', '" + account.getAccountNo() + "', '" + effDate + "', '" + expireDate
                + "', '" + this.status + "'";
        String message = "Policy has been created!";
        storeOnDB("policy", fields, values, message);
    }

    /**
     * Cancel policy
     */
    public void cancelPolicy() {
        try {
            System.out.println();
            printBorderWithText(12," Cancel Policy ");
            System.out.print(" Enter Policy Number    ---> ");
            String policyNum = validateNumber("Policy Number", "PolicyNum").trim();
            printBorder(39);
            selectPolicy(policyNum);
            if (!isPolicyExist) {
                tryAgain("Policy doesn't exist!", "cancel");
            } else {
                checkStatus();
                policyDateDisplay();
                System.out.print(" New Expiration Date ---> ");
                newExpiryDate = validateChangeDate().trim();
                parseNewExpDate = LocalDate.parse(newExpiryDate);
                updateOnDB("policy", "status", "Adjusted", "policy_no", policyNum);
                updateOnDB("policy", "expiry_date", newExpiryDate, "policy_no", policyNum);
                policyNewDateDisplay();
                printSuccess("Policy Expiration Date has been updated!");
                promptEnterKey();
                main.menuChoice();
            }
        } catch (Exception e) {
            tryAgain(e.toString(), "cancel");
        }
    }

    /**
     * Search and display policy details using policy number
     */
    public void display() {
        try {
            System.out.println();
            printBorderWithText(11," Search Policy ");
            System.out.print(" Enter Policy Number       ---> ");
            policyNum = validateNumber("Policy Number","SearchPolicyNumber").trim();
            printBorder(37);
            selectPolicy(policyNum);
            if (!isPolicyExist) {
                printError("Policy doesn't exist");
            } else {
                displayPolicy(policyNum);
                policyDisplay();
                displayVehicle(policyNum);
            }
            promptEnterKey();
            main.menuChoice();
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Retrieves policy details and checks if the policy number already exists
     * @param policyNo string parameter for policy number
     */
    public void selectPolicy(String policyNo) {
        try {
            connectOnDB();
            String query = "SELECT * FROM policy WHERE policy_no='" + policyNo + "'";
            result = stmt.executeQuery(query);
            if (!result.next()) {
                isPolicyExist = false;
            } else {
                isPolicyExist = true;
                getPolicyDetails(result.getString("policy_no"),
                        result.getString("effective_date"),
                        result.getString("expiry_date"),
                        result.getString("status"),
                        result.getDouble("cost")
                );
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Retrieves and displays its policy details through its policy number
     * @param policyNum string parameter for policy number
     */
    public void displayPolicy(String policyNum) {
        try {
            connectOnDB();
            String query = "SELECT p.policy_no, p.effective_date, p.expiry_date, p.status,p.cost, ph.acc_type, " +
                    "ph.first_name, " + "ph.last_name, ph.date_of_birth, ph.address, ph.drivers_license_num, " +
                    "ph.drivers_license_issued " + "FROM policy_holder AS ph INNER JOIN policy as p on p.policy_no = " +
                    "ph.policy_no WHERE ph.policy_no='" + policyNum + "'";
            result = stmt.executeQuery(query);
            while (result.next()) {
                getPolicyDetails(
                        result.getString("policy_no"),
                        result.getString("effective_date"),
                        result.getString("expiry_date"),
                        result.getString("status"),
                        result.getDouble("cost"));

                accType = result.getString("acc_type");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");
                dateOfBirth = result.getString("date_of_birth");
                address = result.getString("address");
                driversLicenseNum = result.getString("drivers_license_num");
                dateIssued = result.getString("drivers_license_issued");
                totalPremiumCharged = result.getString("cost");
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Retrieves and displays its policy's vehicle details through its policy number
     * @param policyNum string parameter for policy number
     */
    public void displayVehicle(String policyNum) {
        try {
            connectOnDB();
            String query = "SELECT make, model, year, type,fuel_type,uuid, color, purchase_price, premium_charged " +
                    "FROM vehicle WHERE policy_no='" + policyNum + "'";
            result = stmt.executeQuery(query);
            ArrayList<Vehicle> vehicleList = new ArrayList<>();

            while (result.next()) {
                vehicleList.add(getVehicleDetails(
                        result.getString("make"),
                        result.getString("model"),
                        result.getString("type"),
                        result.getString("fuel_type"),
                        result.getString("uuid"),
                        result.getInt("year"),
                        result.getDouble("purchase_price"),
                        result.getDouble("premium_charged"),
                        result.getString("color")));
            }
            System.out.println();
            printBorderWithText(51," Vehicle Details ");
            System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", "Make", "Model", "Type", "FuelType",
                    "Year", "Color", "PurchasePrice", "PremiumCharged");
            printDashBorder(121);
            for (Vehicle vehicle : vehicleList) {
                vehicle.vehicleDisplay();
            }

        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Display the policy details
     */
    public void policyDisplay() {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println();
        printBorderWithText(51," Policy  Details ");
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-15s %-30s%n", "Status", "FirstName", "LastName",
                "Relationship", "DateOfBirth", "Address", "DriversLicenseNo", "DriversLicenseDate");
        printDashBorder(121);
        System.out.format("%-10s %-10s %-15s %-15s %-15s %-15s %-16s %-30s%n", policy.getStatus(), firstName, lastName,
                accType, dateOfBirth, address, driversLicenseNum, dateIssued);
        printBorder(119);
        System.out.println();
        System.out.println("                                  ~~~  Total Policy Premium Charged ---> " +
                currency.format(Double.valueOf(totalPremiumCharged)) + "  ~~~                    ");
    }

    /**
     * Display the policy date
     */
    public void policyDateDisplay() {
        System.out.println();
        printBorderWithText(9," Policy Date Details ");
        System.out.format("%-10s %-15s %-15s%n", "PolicyNo", "EffectiveDate", "ExpirationDate");
        printDashBorder(41);
        System.out.format("%-10s %-15s %-15s%n", policy.getPolicyNo(), policy.getEffectiveDate(),
                policy.getExpirationDate());
        printBorder(39);
        System.out.println();
    }

    /**
     * Display the new policy date
     */
    public void policyNewDateDisplay() {
        System.out.println();
        printBorderWithText(5," Updated Policy Date Details ");
        System.out.format("%-10s %-15s %-15s%n", "PolicyNo", "EffectiveDate", "ExpirationDate");
        printDashBorder(41);
        System.out.format("%-10s %-15s %-15s%n", policy.getPolicyNo(), policy.getEffectiveDate(),
                getNewExpiryDate());
        printBorder(39);
    }


    /**
     * Set the status of policy
     */
    public void setPolicyStatus() {
        if (LocalDate.now().compareTo(effectDate) >= 0 && LocalDate.now().compareTo(expireDate) <= 0) {
            this.status = "Active";
        } else if (LocalDate.now().compareTo(effectDate) <= 0 && LocalDate.now().compareTo(expireDate) <= 0) {
            this.status = "Scheduled";
        } else {
            this.status = "Expired";
        }
    }

    /**
     * Repeat the load method of Customer Account or the cancel method
     * @param message string parameter for message
     * @param purpose string instance for setting a condition to use in linking to a method
     */
    public void tryAgain(String message, String purpose) {
        System.out.print("\n Error: " + message + "\n\nTry Again? (1)Yes (2)No ---> ");
        int opt = input.nextInt();
        input.nextLine();

        if (opt == 1 && purpose.equals("load")) {
            load();
        }
        if (opt == 1 && purpose.equals("cancel")) {
            cancelPolicy();
        } else {
            main.menuChoice();
        }
    }

    /**
     * Check the status of policy if already expired
     */
    public void checkStatus() {
        if (policy.status.equals("Expired")) {
            tryAgain("Policy has been " + policy.status, "cancel");
        }
    }

    /**
     * Get policy's expiration date
     * @return instance expirationDate
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Get policy's effective date
     * @return instance effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Get policy's new expiration date
     * @return instance newExpiryDate
     */
    public String getNewExpiryDate() {
        return newExpiryDate;
    }

    /**
     * Get policy's status
     * @return instance status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get boolean isPolicyExist
     * @return instance isPolicyExist
     */
    public boolean getIsPolicyExist() {
        return isPolicyExist;
    }

    /**
     * Get policy number
     * @return instance policy number
     */
    public String getPolicyNo() {
        return policyNo;
    }

}
