/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.time.LocalDate;
import java.time.Period;

public class PolicyHolder extends Validations {
    PASDriver main = new PASDriver();
    Policy policy = new Policy("", "", "", "", 0);
    public String firstName, lastName, address, driversLicenseNum, accType, uuid;
    public LocalDate dateOfBirth, driversLicenseIssued;


    /**
     * Method for policyholder details
     * @param policyNo string instance of policy number from Policy Class
     */
    public void load(String policyNo) {
        try {
            printBorderWithText(16, " Policy Holder Details ");
            System.out.print(" Enter First Name                         ---> ");
            this.firstName = validateAlpha("First Name","PolicyHolder").toUpperCase();
            System.out.print(" Enter Last Name                          ---> ");
            this.lastName = validateAlpha("Last Name ", "PolicyHolder").toUpperCase();
            AccountType();
            System.out.print(" Enter Date of Birth(YYYY-MM-DD)          ---> ");
            String dob = validateDate("Date of Birth", "DateOfBirth");
            this.dateOfBirth = LocalDate.parse(dob);
            System.out.print(" Enter Address                            ---> ");
            this.address = validateAddress("PolicyHolder").toUpperCase();
            System.out.print(" Enter Driver's License Number            ---> ");
            this.driversLicenseNum = validateNumber("Driver's License Number","DriversLicenseNum");
            System.out.print(" Driver's license Date Issued(YYYY-MM-DD) ---> ");
            String dateIssued = validateDate(" Driver's license Date Issued","DriverLicensedDate");
            this.driversLicenseIssued = LocalDate.parse(dateIssued);
            printBorder(55);
            uuid = getUUID();
            store(policyNo);
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Store policy holder details on database
     * @param policyNo string parameter for policy number
     */
    public void store(String policyNo) {
        String fields = "uuid, policy_no, customer_acc_no, acc_type, first_name, last_name, date_of_birth, address, " +
                "drivers_license_num, drivers_license_issued";
        String values = "'" + uuid + "', '" + policyNo + "', '" + account.getAccountNo() + "','"
                + this.accType + "', '" + this.firstName + "', '" + this.lastName + "', '" + this.dateOfBirth
                + "','" + this.address + "', '" + this.driversLicenseNum + "', '" + this.driversLicenseIssued + "'";
        String message = "Policy holder has been created!";
        storeOnDB("policy_holder", fields, values, message);
        policy.updateOnDB("policy", "policy_holder_uuid", uuid, "policy_no", policyNo);
    }

    /**
     * Get the no. of years in between the driver license issued date and today's date
     * @return accurate number of years
     */
    public int getDlx() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(driversLicenseIssued, today);
        return period.getYears();
    }


    /**
     * Validate if the input of first name and last name in PolicyHolder is same as the customer's account name.
     * A relationship is asked if not the same name
     */
    public void AccountType() {
        if (account.getFirstName().equals(firstName) && account.getLastName().equals(lastName)) {
            this.accType = "account owner";
        } else {
            System.out.print(" What is your relationship                ---> ");
            this.accType = validateAlpha("Relationship", "Relationship");
        }
    }

    /**
     * Get first name of policyholder
     * @return instance firstName
     */
    public String getFirstNamePolicyHolder() {
        return firstName;
    }

    /**
     * Get last name of policyholder
     * @return instance lastName
     */
    public String getLastNamePolicyHolder() {
        return lastName;
    }

    /**
     * Get account type of policyholder
     * @return instance accType
     */
    public String getAccType() {
        return accType;
    }

    /**
     * Get address of policyholder
     * @return instance address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get driver's license number of policyholder
     * @return instance driversLicenseNum
     */
    public String getDriversLicenseNum() {
        return driversLicenseNum;
    }

    /**
     * Get universally unique identifier of policyholder
     * @return instance uuid
     */
    public String getOwnUUID() {return uuid;}

    /**
     * Get date of birth of policyholder
     * @return instance dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Get driver's license number of policyholder
     * @return instance driversLicenseIssued
     */
    public LocalDate getDriversLicenseIssued() {
        return driversLicenseIssued;
    }




}
