/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.util.Scanner;

public class CustomerAccount extends Validations {
    PASDriver main = new PASDriver();
    Scanner input = new Scanner(System.in);
    private boolean isAccountExist = true;
    public String accountNumber, firstName, lastName, address;
    public String policyNo, fName, lName, accType;

    /**
     * Parameterized Constructor of Class CustomerAccount
     * @param accountNumber string parameter for customer's account number
     * @param firstName string parameter for customer's first name
     * @param lastName string parameter for customer's last name
     * @param address string parameter for customer's address
     */
    public CustomerAccount(String accountNumber, String firstName, String lastName, String address) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    /**
     * Method for Creating Customer Account
     */
    public void load() {
        try {
            printBorderWithText(6, " Create Customer Account ");
            // generates a random number
            int generateNum = random.nextInt(9999);
            this.accountNumber = String.format("%04d", generateNum);
            System.out.print(" Enter First Name ---> ");
            this.firstName = validateAlpha("First Name", "CustomerAccount").toUpperCase();
            System.out.print(" Enter Last Name  ---> ");
            this.lastName = validateAlpha("Last Name ", "CustomerAccount").toUpperCase();
            System.out.print(" Enter Address    ---> ");
            this.address = validateAddress("CustomerAccount");
            // calls validateAccountByName to check if it already exists or not
            validateAccountByName();
            System.out.println(" Account Number   ---> " + accountNumber);
            printBorder(37);
            if (!isAccountExist) {
                store();
            } else {
                tryAgain("Account already exists!", "load");
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Validate account if exist by firstname and lastname
     */
    private void validateAccountByName() {
        try {
            connectOnDB();
            String query = "SELECT * FROM customer_acct WHERE first_name = '" + firstName + "' AND last_name = '"
                    + lastName + "'  ";
            result = stmt.executeQuery(query);

            if (!result.next()) {
                this.isAccountExist = false;
            } else {
                isAccountExist = true;
                getAccountDetails(result.getString("account_no"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("address"));
            }
        } catch (Exception e) {
            printError(e.toString());
            input.nextLine();
            main.menuChoice();
        }
    }

    /**
     * Store customer account details on DB
     */
    public void store() {
        String fields = "account_no, first_name, last_name, address";
        String values = "'" + this.accountNumber + "', '" + this.firstName + "', '" + this.lastName + "', '"
                + this.address + "'";
        String message = "Customer account has been created!";
        // calls the storeOnDb method to store the Customer Account details on customer_acct table
        storeOnDB("customer_acct", fields, values, message);
        //prompts to enter key
        promptEnterKey();
        //redirects the program to menuChoice
        main.menuChoice();
    }

    /**
     * Checks account if exist using account number
     */
    public void checkAccountIfExist() {
        try {
            // calls the connectOnDb method
            connectOnDB();
            System.out.print(" Enter Account Number                     ---> ");
            String accNo = validateNumber("Account Number", "Policy").trim();
            String query = "SELECT * FROM customer_acct WHERE account_no = '" + accNo + "' ";
            result = stmt.executeQuery(query);  // executes the query

            if (!result.next()) {
                this.isAccountExist = false;
            } else {
                isAccountExist = true;
                getAccountDetails(result.getString("account_no"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("address"));
            }
        } catch (Exception e) {
            printError(e.toString());
            input.nextLine();
            main.menuChoice();
        }
    }

    /**
     * Displays the customer account details
     */
    public void display() {
        try {
            System.out.println();
            printBorderWithText(6, " Search a Customer Account ");
            System.out.print(" Enter First Name ---> ");
            this.firstName = validateAlpha("First Name","SearchCustomerAccount").toUpperCase();
            System.out.print(" Enter Last Name  ---> ");
            this.lastName = validateAlpha("Last Name","SearchCustomerAccount").toUpperCase();
            printBorder(39);
            // calls validate accountByName method to assess if the entered first and last name already exist
            validateAccountByName();
            if (!isAccountExist) {

                tryAgain("Account doesn't exist!", "display");
            } else {
                System.out.println();
                printBorderWithText(21, " Account Details ");
                System.out.format("%-15s %-15s %-15s %-15s%n", "AccountNo", "Firstname", "Lastname", "Address");
                printDashBorder(61);
                System.out.format("%-15s %-15s %-15s %-15s%n", account.accountNumber, account.getFirstName(),
                        account.getLastName(), account.getAddress());
                printBorder(59);
                System.out.println();
                printBorderWithText(19," Policy/ies Acquired ");
                System.out.format("%-15s %-15s %-15s %-15s%n", "PolicyNo", "Firstname", "Lastname", "Relationship");
                printDashBorder(61);
                String accNum = account.accountNumber;
                // get the details of Customer Account in database by its account number
                selectCustomerAccount(accNum);
                promptEnterKey();
                main.menuChoice();
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }

    /**
     * Repeat the load method of Customer Account or the display method
     * @param message string parameter for message
     * @param purpose string instance for setting a condition to use in linking to a method
     */
    public void tryAgain(String message, String purpose) {
        System.out.print("\nError: " + message + "\n\nTry Again? (1)Yes (2)No ---> ");
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
     * Get the Customer Account Details in database using the account number
     * @param accNum string parameter for account number
     */
    public void selectCustomerAccount(String accNum) {
        try {
            connectOnDB();
            String query = "SELECT * FROM policy_holder WHERE customer_acc_no='" + accNum + "'";
            result = stmt.executeQuery(query);
            while (result.next()) {
                policyNo = result.getString("policy_no");
                fName = result.getString("first_name");
                lName = result.getString("last_name");
                accType = result.getString("acc_type");
                System.out.format("%-15s %-15s %-15s %-15s%n", policyNo, fName, lName, accType);
                printBorder(59);
            }
        } catch (Exception e) {
            printError(e.toString());
            main.menuChoice();
        }
    }


    /**
     * Get account number
     * @return instance accountNumber
     */
    public String getAccountNo() {
        return accountNumber;
    }

    /**
     * Get first name
     * @return instance firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get lastname
     * @return instance lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get address
     * @return instance address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get boolean isAccountExist
     * @return instance isAccountExist
     */
    public boolean isAccountExist() {
        return isAccountExist;
    }

}
