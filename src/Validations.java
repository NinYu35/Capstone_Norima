import java.time.LocalDate;
import java.util.Scanner;

public class Validations extends Helper{

    PASDriver main = new PASDriver();

    /**
     * Tells whether the string matches the condition
     * if the input of the user is not an alphabet or is empty it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public  boolean checkerAlpha(String entered){
        return !entered.matches("[a-zA-Z ]+") || entered.isEmpty();
    }

    /**
     * Tells whether the string matches the condition
     * if the input of the user do not match with the regex in the condition  or is empty, it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public  boolean checkerAddress(String entered){
        return !entered.matches("[a-zA-Z\\d -,#.]+") || entered.isEmpty();
    }

    /**
     * Tells whether the string matches the condition
     * if the input of the user do not match with a number or is empty, it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public  boolean checkerNum(String entered){
        return !entered.matches("\\d+") || entered.isEmpty();
    }

    /**
     * Tells whether the string matches the condition
     * if the input of the user do not match with the regex in the condition or is empty, it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public  boolean checkerClaimNum(String entered){
        return !entered.matches("C\\d\\d\\d\\d\\d\\d") || entered.isEmpty();
    }

    /**
     * Tells whether the string matches the condition
     * if the input of the user do not match with the regex in the condition or is empty, it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public  boolean checkerDate(String entered){
        return !entered.matches("\\d+-+\\d+-+\\d+") || entered.isEmpty();
    }


    /**
     * Tells whether the string matches the condition,
     * if the input of the user do not match with the regex in the condition or is empty, it returns 'true'
     * @param entered a string instance of user input
     * @return true if, and only if, the string matches and satisfies one of the conditions
     */
    public boolean checkerAlphaNumSpaceDash(String entered){
        return !entered.matches("[a-zA-Z\\d -]+") || entered.isEmpty() ;
    }

    /**
     * Method to validate if the String input of the user consists an alphabet only or entered an empty string
     * @param value a string instance for the field of what is asked, to be used in printing error details
     * @param purpose a string instance for setting a condition to use, combined with the boolean method.
     * @return entered - a recursive call , loops until its satisfies the condition
     */
    public String validateAlpha(String value, String purpose){
        String entered = input.nextLine();
        if (checkerAlpha(entered.trim()) && purpose.equals("CustomerAccount")){
            System.out.println(checkerAlpha(entered));
            printBorder(37);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(37);
            System.out.print(" Enter " + value + " ---> ");
            entered = validateAlpha(value, purpose);
        }

        if  (checkerAlpha(entered.trim()) && purpose.equals("PolicyHolder")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" Enter " + value + "                         ---> ");
            entered = validateAlpha(value, purpose);
        }
        if  (checkerAlpha(entered.trim()) && purpose.equals("Relationship")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" What is your  " + value + "               ---> ");
            entered = validateAlpha(value, purpose);
        }
        if  (checkerAlpha(entered.trim()) && purpose.equals("DescAccident")){
            printBorder(61);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(61);
            System.out.print(" Enter " + value + "          ---> ");
            entered = validateAlpha(value, purpose);
        }
        if  (checkerAlpha(entered.trim()) && purpose.equals("DescVehicleDamage")){
            printBorder(61);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(61);
            System.out.print(" Enter " + value + "    ---> ");
            entered = validateAlpha(value, purpose);
        }
        if (checkerAlpha(entered.trim()) && purpose.equals("SearchCustomerAccount")){
            printBorder(39);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(39);
            System.out.print(" Enter " + value + " ---> ");
            entered = validateAlpha(value, purpose);
        }
        if (checkerAlpha(entered.trim()) && purpose.equals("Alpha")){
            printBorder(55);
            printError("Please enter a valid " + value + "!");
            printBorder(55);
            System.out.print(" Vehicle's " + value + "                           ---> ");
            entered = validateAlpha(value, purpose);
        }
        return entered;
    }


    /**
     * Method to validate if the String input of the user for address satisfies the regex allowed
     * (combinations of alphabet, numbers,space, comma or dot ) or entered an empty string
     * @param purpose a string instance for setting a condition to use, combined with the boolean method.
     * @return entered - a recursive call , loops until its satisfies the condition
     */
    public String validateAddress(String purpose){
        String entered = input.nextLine();
        if (checkerAddress(entered) && purpose.equals("CustomerAccount")){
            printBorder(37);
            printError("Please enter a valid address!");
            printBorder(37);
            System.out.print(" Enter Address    ---> ");
            entered = validateAddress(purpose);
        }
        if (checkerAddress(entered) && purpose.equals("PolicyHolder")){
            printBorder(55);
            printError("Please enter a valid address!");
            printBorder(55);
            System.out.print(" Enter Address                            ---> ");
            entered = validateAddress(purpose);
        }
        if (checkerAddress(entered) && purpose.equals("AccidentClaim")){
            printBorder(61);
            printError("Please enter a valid address!");
            printBorder(61);
            System.out.print(" Enter Address                          ---> ");
            entered = validateAddress(purpose);
        }
        return entered;
    }

    /**
     * Method to validate if the String input of the user consists a number only or entered an empty string
     * @param value a string parameter for the field of what is asked, to be used in printing error details
     * @param purpose a string parameter for setting a condition to use, combined with the boolean method.
     * @return entered - a recursive call , loops until its satisfies the condition
     */
    public String validateNumber(String value, String purpose){
        String entered = input.nextLine();
        if (checkerNum(entered) && purpose.equals("Policy")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" Enter " + value + "                     ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerAlphaNumSpaceDash(entered) && purpose.equals("DriversLicenseNum")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" Enter " + value + "            ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerNum(entered) && purpose.equals("PolicyNum")){
            printBorder(39);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(39);
            System.out.print(" Enter " + value + "                    ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerNum(entered) && purpose.equals("PolicyNumber")){
            printBorder(61);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(61);
            System.out.print(" Enter " + value + "                    ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerNum(entered) && purpose.equals("SearchPolicyNumber")){
            printBorder(37);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(37);
            System.out.print(" Enter " + value + "       ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerClaimNum(entered.toUpperCase()) && purpose.equals("SearchClaimNumber")){
            printBorder(37);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(37);
            System.out.print(" Enter " + value + " ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerAlphaNumSpaceDash(entered) && purpose.equals("AlphaNumSpace")){
            printBorder(55);
            printError("Please enter a valid " + value + "!");
            printBorder(55);
            System.out.print(" Vehicle's " + value + "                          ---> ");
            entered = validateNumber(value, purpose);
        }
        if (checkerNum(entered) && purpose.equals("PolicyNum")){
            printBorder(39);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(39);
            System.out.print(" Enter " + value + "    ---> ");
            entered = validateNumber(value, purpose);
        }
        return entered;
    }

    /**
     * Method to validate if the String input of the user for date satisfies the regex condition allowed
     * or entered an empty string
     * @param value a string parameter for the field of what is asked, to be used as subject in printing error details
     * @param purpose a string parameter for setting a condition to use, combined with the boolean method.
     * @return entered - a recursive call , loops until its satisfies the condition
     */
    public String validateDate(String value, String purpose){
        String entered = input.nextLine();
        if (checkerDate(entered) && purpose.equals("ChangeDate")){
            printBorder(39);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(39);
            System.out.print(" Enter " +value + " ---> ");
            entered = validateDate(value, purpose);
        }
        policy.parseNewExpDate = LocalDate.parse(entered);
        policy.parseEffDate = LocalDate.parse(policy.effectiveDate);
        policy.sixMonthsPolicyTerm = policy.parseEffDate.plusMonths(6);
        int diffInMonthsNewExpiryDate = policy.parseEffDate.compareTo(policy.parseNewExpDate);
        int diffInMonthsOriginalExpiryDate = policy.sixMonthsPolicyTerm.compareTo(policy.parseNewExpDate);
        if (!(diffInMonthsNewExpiryDate <= 0 && diffInMonthsOriginalExpiryDate >= 0) && purpose.equals("ChangeDate")){
            printBorder(39);
            printError("The new expiry date should be earlier date " +
                    "\n       than originally specified.");
            printBorder(39);
            System.out.print(" " +value + " ---> ");
            entered = validateDate(value, purpose);
        }
        LocalDate dateToday = LocalDate.now();
        claim.parseDateOfAccident = LocalDate.parse(entered);
        claim.parseEffectiveDate = LocalDate.parse(policy.getEffectiveDate());
        claim.parseNewExpirationDate = LocalDate.parse(policy.getExpirationDate());
        if ((claim.parseEffectiveDate.compareTo(claim.parseDateOfAccident) > 0)&& purpose.equals("AccidentDate")){
            printBorder(61);
            printError("Your Insurance Policy starts on " + claim.parseEffectiveDate + "."+
                    " \n       You cannot file a claim.");
            printBorder(61);
            promptEnterKey();
            main.menuChoice(); // return to menu
        }
        if ((dateToday.compareTo(claim.parseDateOfAccident) < 0)&& purpose.equals("AccidentDate")){
            printBorder(61);
            printError("You cannot file using a future date." +
                    " \n       Only date today and previous are accepted!");
            printBorder(61);
            promptEnterKey();
            main.menuChoice(); // return to menu
        }
        if (checkerDate(entered) && purpose.equals("AccidentDate")){
            printBorder(61);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(61);
            System.out.print(" Enter " + value + "(YYYY-MM-DD)     ---> ");
            entered = validateDate(value, purpose);
        }
        if (checkerDate(entered) && purpose.equals("DateOfBirth")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" Enter " +value + "(YYYY-MM-DD)          ---> ");
            entered = validateDate(value, purpose);
        }
        if (checkerDate(entered) && purpose.equals("DriverLicensedDate")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(value + "(YYYY-MM-DD) ---> ");
            entered = validateDate(value, purpose);
        }

        if (checkerDate(entered) && purpose.equals("Policy")){
            printBorder(55);
            printError("Please enter a valid " + value.toLowerCase() + "!");
            printBorder(55);
            System.out.print(" Enter " + value + " (YYYY-MM-DD)        ---> ");
            entered = validateDate(value, purpose);
        }
        policy.effectDate = LocalDate.parse(entered);
        if (LocalDate.now().compareTo(policy.effectDate) > 0 && purpose.equals("Policy")) {
            printBorder(55);
            printError("Please Enter date today or beyond.");
            printBorder(55);
            System.out.print(" Enter " + value + " (YYYY-MM-DD)        ---> ");
            entered = validateDate(value,purpose);
        }

        return entered;
    }


    /**
     * @param input user input parameter
     * @return prompts user input, throw an error message if the next token cannot be translated into a valid int value
     * loops until the token of the input is an int
     */
    public int nextValidYear(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("\n Error: " + input.next() + " is not a valid year!\n"
                                   + " \n Vehicle's Purchase Year                  ---> ");
        }
        return input.nextInt();
    }

    /**
     * @param input user input parameter
     * @return prompts user input, throw an error message if the next token cannot be translated into a valid int value
     * and loops until the token of the input is an int
     */
    public int nextValidInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("\n Error: " + input.next() + " is not a valid number!\n"
                                   + " \n Please enter a valid input (1-9)         ---> ");
        }
        return input.nextInt();
    }

    /**
     * @param input user input parameter
     * @return prompts user input, throw an error message if the next token cannot be translated into a valid double
     * value  and loops until the token of the input is a double
     */
    public double nextValidPrice(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.print("\n Error: " + input.next() + " is not a valid number!\n"
                                   + " \n Vehicle's Purchase Price                 ---> ");
        }
        return input.nextDouble();
    }

    /**
     * @param input user input parameter
     * @return prompts user input, throw an error message if the next token cannot be translated into a valid double
     * value and loops until the token of the input is a double
     */
    public double nextValidCostOfRepair(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.print("\n Error: " + input.next() + " is not a valid number!\n"
                    + " \n Enter Estimated Cost of Repair         ---> ");
        }
        return input.nextDouble();
    }
}
