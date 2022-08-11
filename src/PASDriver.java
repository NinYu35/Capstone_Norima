/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.util.Scanner;


public class PASDriver extends Helper {

    public static void main(String[] args) {

        PASDriver main = new PASDriver();
        Scanner input = new Scanner(System.in);
        account = new CustomerAccount("", "", "", "");
        policy = new Policy("", "", "", "", 0);
        claim = new Claim("", "", "", "",
                "", 0.0);
        main.menuChoice();
        input.close();
    }


    /**
     * Menu of PAS Application
     */
    public void menuChoice() {
        int choice;
        try {
            // Call and execute auto update of policy status
            autoUpdatePolicyStatus();
            printMenu();
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    account.load();
                    break;

                case 2:
                    policy.load();
                    break;
                case 3:
                    policy.cancelPolicy();
                    break;
                case 4:
                    claim.load();
                    break;

                case 5:
                    account.display();
                    break;

                case 6:
                    policy.display();

                case 7:
                    claim.display();
                    break;

                case 8:
                    System.out.println(" \n Program is exiting ...");
                    System.exit(0);
                default:
                    printError("Invalid input! Please Enter (1-8) of your Choice.");
                    menuChoice();
            }

        } catch (Exception e) {
            printError("Invalid input! Please Enter (1-8) of your Choice.");
            input.nextLine();
            menuChoice();
        }
    }


    /**
     * Details of Menu
     */
    public void printMenu() {
        printBorder(84);
        System.out.println("|The Automobile Insurance Policy and Claims Administration System (PAS) Specification|");
        printBorder(84);
        System.out.println("| (1) Create a new Customer Account                                                  |");
        System.out.println("| (2) Get a Policy Quote and Buy the Policy                      ________            |");
        System.out.println("| (3) Cancel a Specific Policy                                  /   |    |\\          |");
        System.out.println("| (4) File an Accident Claim against a Policy           _____+_/____|____|__\\        |");
        System.out.println("| (5) Search for a Customer Account                    /      |    -| norima |       |");
        System.out.println("| (6) Search for and Display a Specific Policy        {_______|_____|_______/=       |");
        System.out.println("| (7) Search for and Display a Specific Claim              (o)         (o)           |");
        System.out.println("| (8) Exit the PAS System                                                            |");
        printBorder(84);
        System.out.print(" Enter your Choice (1-8) ---> ");
    }

}


