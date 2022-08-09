/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Helper  {
    public static CustomerAccount account;
    public static Policy policy;
    public static Claim claim;
    public static Vehicle vehicle;
    static Scanner input = new Scanner(System.in);
    static final String DB_URL ="jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASSWORD = "root";
    Random random = new Random();
    Connection conn;
    PreparedStatement prep;
    Statement stmt;
    ResultSet result;
    PolicyHolder policyHolder;


    /**
     * Method for establishing connection and creating database, tables, fields and values
     */
        public void connectOnDB() {
            String qryCreateDB = "CREATE DATABASE IF NOT EXISTS `pas_db`";
            String qryUseDB = "USE `pas_db`";
            String qryCustomerTable = "CREATE TABLE IF NOT EXISTS `customer_acct`("
                    + "`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "`account_no` varchar(50) NOT NULL,"
                    + "`first_name` varchar(50) NOT NULL,"
                    + "`last_name` varchar(50) NOT NULL,"
                    + "`address` varchar(50) NOT NULL"
                    + ")";
            String qryPolicyTable = "CREATE TABLE IF NOT EXISTS `policy` ("
                    + "`id` int NOT NULL AUTO_INCREMENT,"
                    + "`policy_no` varchar(50) NOT NULL,"
                    + "`customer_acc_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',"
                    + "`policy_holder_uuid` binary(36) DEFAULT NULL,"
                    + "`effective_date` date DEFAULT NULL,"
                    + "`expiry_date` date DEFAULT NULL,"
                    + "`status` varchar(50) NOT NULL,"
                    + "`cost` varchar(50) DEFAULT NULL,"
                    + "`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + " PRIMARY KEY (`id`)"
                    + ")";

            String qryHolderTable = "CREATE TABLE IF NOT EXISTS `policy_holder` ("
                    + "`uuid` char(36) NOT NULL,"
                    + "`policy_no` varchar(50) NOT NULL,"
                    + "`customer_acc_no` varchar(50) NOT NULL,"
                    + "`acc_type` varchar(50) NOT NULL,"
                    + "`first_name` varchar(50) NOT NULL,"
                    + "`last_name` varchar(50) NOT NULL,"
                    + "`date_of_birth` date NOT NULL,"
                    + "`address` varchar(50) NOT NULL,"
                    + "`drivers_license_num` varchar(50) NOT NULL,"
                    + "`drivers_license_issued` date NOT NULL,"
                    + "`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (`uuid`)"
                    + ")";
            String qryClaimTable = "CREATE TABLE IF NOT EXISTS `claim` ("
                    + "`id` int NOT NULL AUTO_INCREMENT,"
                    + "`policy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,"
                    + "`claim_no` varchar(50) NOT NULL,"
                    + "`date_of_accident` date NOT NULL,"
                    + "`address_of_accident` varchar(50) NOT NULL,"
                    + "`desc_of_accident` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,"
                    + "`desc_vehicle_damage` text,"
                    + "`est_cost_repairs` double NOT NULL DEFAULT '0',"
                    + "`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (`id`)"
                    + ")";
            String qryVehicleTable = "CREATE TABLE IF NOT EXISTS `vehicle` ("
                    + "`uuid` char(36) NOT NULL,"
                    + "`customer_acc_no` varchar(50) NOT NULL DEFAULT '',"
                    + "`policy_holder_uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',"
                    + "`policy_no` varchar(50) NOT NULL DEFAULT '',"
                    + "`make` varchar(50) NOT NULL,"
                    + "`model` varchar(50) NOT NULL,"
                    + "`year` int NOT NULL,"
                    + "`type` varchar(50) NOT NULL,"
                    + "`fuel_type` varchar(50) NOT NULL,"
                    + "`color` varchar(50) NOT NULL,"
                    + "`purchase_price` double NOT NULL DEFAULT '0',"
                    + "`premium_charged` double NOT NULL DEFAULT '0',"
                    + "`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (`uuid`)"
                    + ")";

            try {

                //Construct a database 'Connection' object called 'conn'
                conn = DriverManager.getConnection
                        (DB_URL,USER,PASSWORD);

                //Construct a 'Statement' object called 'stmt' inside the Connection created
                stmt = conn.createStatement();
                stmt.executeUpdate(qryCreateDB); //create database
                stmt.execute(qryUseDB);
                stmt.execute(qryCustomerTable); //create customer table
                stmt.execute(qryPolicyTable); //create policy table
                stmt.execute(qryHolderTable); //create policyholder table
                stmt.execute(qryVehicleTable); //create vehicle table
                stmt.execute(qryClaimTable); //create claim table

            } catch (Exception e) {
                e.printStackTrace();
            }
        } // End connectOnDB

    /**
     * Method for storing data in db
     * @param table string parameter for table name
     * @param fields string parameter for fields in the table
     * @param values string parameter for the values of the fields in the table
     * @param message string parameter for printing a message success if successfully stored on db
     */
    public void storeOnDB(String table, String fields, String values, String message) {
        try {
            // Call connectOnMethod
            connectOnDB();

            String query = "INSERT INTO " + table + " (" + fields + ") VALUES(" + values + ")";
            prep = conn.prepareStatement(query);
            prep.execute();
            if (message.length() > 0) printSuccess(message);
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    /**
     * Method for storing data in db
     * @param table string parameter for table name
     * @param fields string parameter for fields in the table
     * @param value string parameter for the value of the field in the table
     * @param baseField string parameter for the basis of what field to make the condition
     * @param baseNum string parameter for the value of baseField, Example: the policyNum
     */
    public void updateOnDB(String table, String fields, String value, String baseField, String baseNum) {
        try {

            // Call connectOnMethod
            connectOnDB();
            String query = "UPDATE "+ table +" SET "+ fields +" = '"+ value +"' WHERE "+ baseField +"='"+ baseNum +"'";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    /**
     * Method for deleting data in db
     * @param table string parameter for table name
     * @param field string parameter for field in the table
     * @param value string parameter for the value of the field in the table
     */
    public void deleteFromDb(String table, String field, String value) {
        try {
            connectOnDB();
            String query = "DELETE FROM " + table + " WHERE " + field + "='" + value + "'";
            prep = conn.prepareStatement(query);
            prep.executeUpdate();

        } catch (Exception e) {
            printError(e.toString());
        }
    }

    /**
     * Method for auto update of policy status base on today's date whenever the application runs
     */
    public void autoUpdatePolicyStatus() {
        try {
            connectOnDB();
            String strUpdateSafe = "SET SQL_SAFE_UPDATES=0";
            String strActivatePolicy = "UPDATE policy SET status = 'Active' WHERE DATE(effective_date) <= DATE(now())";
            String strDeactivatePolicy = "UPDATE policy SET status = 'Expired' WHERE DATE(expiry_date) <= DATE(now())";
            stmt.executeUpdate(strUpdateSafe);
            stmt.executeUpdate(strActivatePolicy);
            stmt.executeUpdate(strDeactivatePolicy);

        } catch (SQLException e) {
            System.out.print("Error updating policy status: " + e.getMessage());
        }
    }


    /**
     * Method for printing success message
     * @param message string parameter for message
     */
    public void printSuccess(String message) {
        System.out.println("\n Success: " + message + "\n");
    }

    /**
     * Method for printing error message
     * @param message string parameter for message
     */
    public static void printError(String message) {
        System.out.println("\nError: " + message + "\n");
    }

    /**
     * Method for generating random universally unique identifier (UUID)
     * @return uuid
     */
    public String getUUID() {
        String getUuid = UUID.randomUUID().toString();
        getUuid = getUuid.replaceAll("-", "");
        return getUuid;
    }

    /**
     * Parameterized method to get the customer account details
     * @param accNo string parameter for account number
     * @param fName string parameter for first name
     * @param lName string parameter for last name
     * @param address string parameter for address
     */
    public void getAccountDetails(String accNo, String fName, String lName, String address) {
        account = new CustomerAccount(accNo, fName, lName, address);
    }

    /**
     * Parameterized method to get the policy details
     * @param policyNo string parameter for policy number
     * @param effectiveDate string parameter for effective date
     * @param expiryDate string parameter for expiration date
     * @param status string parameter for status
     * @param cost string parameter for status
     */
    public void getPolicyDetails(String policyNo, String effectiveDate, String expiryDate, String status, double cost){
        policy = new Policy(policyNo, effectiveDate, expiryDate, status, cost);
    }

    /**
     * Parameterized method to get the claim details
     * @param claimNo string parameter for claim number
     * @param dateOfAccident string parameter for date of accident
     * @param addressAccidentHappened string parameter for address of accident
     * @param descriptionOfAccident string parameter for description of accident
     * @param descriptionOfVehicleDamage string parameter for description of vehicle damage
     * @param estimatedCostOfRepair double parameter for estimated cost of repair
     */
    public void getClaimDetails(String claimNo, String dateOfAccident, String addressAccidentHappened,
                                String descriptionOfAccident, String descriptionOfVehicleDamage, double estimatedCostOfRepair) {
        claim = new Claim(claimNo, dateOfAccident, addressAccidentHappened, descriptionOfAccident,
                descriptionOfVehicleDamage, estimatedCostOfRepair);
    }

    /**
     * Parameterized method to get the vehicle details and returns the values
     * @param make string parameter for make/ brand name of vehicle
     * @param model string parameter for model of the vehicle
     * @param type string parameter for type of the vehicle
     * @param fuelType string parameter for fuel type of the vehicle
     * @param uuid string parameter for uuid of the vehicle
     * @param year int parameter for age of the vehicle / the year it was purchased
     * @param purchasePrice double parameter for purchased price of th vehicle
     * @param premiumCharged double parameter for the premium charged for the specific vehicle
     * @param color string parameter for the color of the vehicle
     * @return value of vehicle
     */
    public Vehicle getVehicleDetails(String make, String model, String type, String fuelType, String uuid,
                                     int year, double purchasePrice, double premiumCharged, String color) {
        vehicle = new Vehicle(make, model, type, fuelType, uuid, year, purchasePrice, premiumCharged, color);
        return vehicle;
    }

    /**
     * Method for letting the user hits the enter key to proceed
     */
    public void promptEnterKey() {
        System.out.print("\nPress \"ENTER\" to continue...");
        input.nextLine();
        System.out.println("\n");
    }

    /**
     * Method to create a tilde borderline
     * @param size int parameter for the no. of  ( tilde/waved dash character ) to display inorder to form a borderline
     */
    public void printBorder( int size) {
        String midStr = "~";
        String endStr = "+";
        String border = "+" + midStr.repeat(Math.max(0, size)) + endStr;
        System.out.println(border);
    }

    /**
     * Method to create a tilde borderline with title text at the middle
     * @param size int parameter for the no. of ( tilde/waved dash character ) to display inorder to form a borderline
     * @param title string parameter for the title text in the middle of the borderline
     */
    public void printBorderWithText( int size, String title) {
        String midStr1 = "~";
        String midStr2 = "~";
        String endStr = "+";
        String border = "\n+" + midStr1.repeat(Math.max(0, size)) + title + midStr2.repeat(Math.max(0, size))
                + endStr;
        System.out.println(border);
    }

    /**
     * Method to create a dash borderline
     * @param size int parameter for the no. dash character to display inorder to form a borderline
     */
    public void printDashBorder( int size) {
        String dashStr = "-";
        String border = dashStr.repeat(Math.max(0, size));
        System.out.println(border);
    }
}




