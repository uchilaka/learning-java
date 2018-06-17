package org.uchechilaka.HelloWorld;

import java.util.InputMismatchException;
import java.util.Scanner;

class RePromptRequiredException extends Exception {
}

class MissingOrInvalidPropertyException extends Exception {
    private String propertyName;
    private String details;

    MissingOrInvalidPropertyException(String name, String details) {
// @TODO do something with this information
        this.propertyName = name;
        this.details = details;
    }

    MissingOrInvalidPropertyException(String details) {
// @TODO do something with this information
        this.details = details;
    }

    @Override
    public String toString() {
        if (propertyName.isEmpty() || details.isEmpty()) return super.toString();
        else {
            return "Problem with property " + propertyName + ": " + details + "; " + super.toString();
        }
    }
}

/**
 * Class for managing travel guest data
 */
class TravelGuest {
    TravelGuest() {
        // @TODO do constructor stuff
    }

    String[] names;
//    private String firstName;
//    String lastName;
    String destinationCurrencyAlpha3 = null;
    Double travelHoursAvailable;
    Double travelBudgetInDollars;
    Double destinationExchangeRateToUSD;
    String homeTimeZone;
    String destinationTimeZone;
    String destinationName;

    String getExchangeRatePrompt() throws Exception {
        if (destinationCurrencyAlpha3 == null) {
            throw new MissingOrInvalidPropertyException("destinationCurrencyAlpha3", "Required property destinationCurrencyAlpha3 MUST be set before you can use this prompt helper function");
        }
        return "How many " + destinationCurrencyAlpha3.toUpperCase() + " are there in 1 USD? ";
    }

    String getFirstName() {
        return names.length > 1 ? (firstName == null ? names[1] : firstName) : names[0];
    }

    String getLastName() {
        return names[0];
    }
}

public class VacationPlanner {

    private static String INPUT_PROMPT_TRAVEL_DAYS = "How many days are you going to spend traveling? ";
    private static String INPUT_PROMPT_TRAVEL_BUDGET = "How much money, in USD, are you planning to spend on your trip? ";
    private static String INPUT_PROMPT_CURRENCY_ALPHA3 = "What is the three letter currency symbol for your travel destination? ";
    private static String REGEX_STRING_NEGATIVE_INPUT = "(?i:no?.*)";
//    private static String REGEX_STRING_AFFIRMATIVE_INPUT = "(?i:y(es)?)";

    private Scanner input;
    private TravelGuest guest;

    VacationPlanner() {
        input = new Scanner(System.in);
        guest = new TravelGuest();
    }

    void begin() {
        greet();
        travelTimeAndBudget();
    }

    private void confirmExit() throws RePromptRequiredException {
        System.out.println("Would you like to quit? (Enter N to continue, or just Enter to confirm) ");
        String response = input.nextLine();
        if (response.isEmpty()) {
            input.close();
            System.exit(0);
        }
        // Still running
        throw new RePromptRequiredException();
    }

    private void captureNames() {
        try {
            guest.names = input.nextLine().split("\\s+");
            System.out.print("To verify, your last name is " + guest.getLastName() + ", is that correct (Enter N if not, or just Enter to confirm)? ");
            String response = input.nextLine();
            if (!response.isEmpty()) {
//                System.out.println("PSST: Found input!");
                if (response.matches(REGEX_STRING_NEGATIVE_INPUT)) {
                    throw new Exception("Last name not entered as instructed");
                }
            }
            // all set!
        } catch (Exception e) {
            System.out.println("Looks like we got an incorrect or invalid name. Try again?");
            captureNames();
        }
    }

    private void sectionBreak() {
        System.out.println("************"); // used 12 stars instead of 11 in the example output (symmetry)
        System.out.println();
    }

    private void greet() {
        System.out.print("Hi! To get started, what's your name (starting with your last name)? ");
        captureNames();
        System.out.print("Nice to meet you, " + guest.getFirstName() + "! Where are you traveling to? ");
        guest.destinationName = input.next();
        System.out.print("Great! " + guest.destinationName + " sounds like a great trip :)");
        sectionBreak();
    }

    private void inputTravelDays(String prompt) {
        System.out.print(prompt);
        inputTravelDays();
    }

    private void inputTravelDays() {
        try {
            Double days = input.nextDouble();
            // @TODO calculate travel hours
            guest.travelHoursAvailable = days * 24.0;
        } catch (Exception e) {
            // Some notes on this: https://softwareengineering.stackexchange.com/a/271877
            if (e instanceof InputMismatchException) {
                System.out.print("Looks like you input an invalid number of days. Try again? ");
                inputTravelDays();
            } else if (e instanceof NullPointerException) {
                // Do something else
                throw e;
            }
        }
    }

    private void inputTravelBudget(String prompt) {
        System.out.print(prompt);
        inputTravelBudget();
    }

    private void inputTravelBudget() {
        try {
            guest.travelBudgetInDollars = input.nextDouble();
        } catch (Exception e) {
            if (e instanceof InputMismatchException) {
                System.out.print("Invalid travel budget amount (must be a number, like 2400). Try again? ");
                inputTravelBudget();
            } else if (e instanceof NullPointerException) {
                throw e;
            }
        }
    }


    private void inputDestinationCurrency(String prompt) {
        System.out.println(prompt);
        inputDestinationCurrency();
    }

    private void inputDestinationCurrency() {
        try {
            guest.destinationCurrencyAlpha3 = input.next();
            if (guest.destinationCurrencyAlpha3.isEmpty())
                throw new MissingOrInvalidPropertyException("You MUST provide a 3 letter string for the currency symbol");
            System.out.print(guest.getExchangeRatePrompt());
            if (!input.hasNextDouble())
                throw new InputMismatchException("Exchange rate to the USD must be a number, like 5.45 (which would mean 1 USD = 5.45 " + guest.destinationCurrencyAlpha3 + ")");
            guest.destinationExchangeRateToUSD = input.nextDouble();
            System.out.println();
        } catch (Exception e) {
            if (e instanceof InputMismatchException) {
                System.out.print("Invalid input (must be 3 letter string). Try again? ");
                inputDestinationCurrency();
            } else if (e instanceof MissingOrInvalidPropertyException) {
                try {
                    confirmExit();
                } catch (RePromptRequiredException e2) {
                    inputDestinationCurrency(INPUT_PROMPT_CURRENCY_ALPHA3);
                }
            } else if (e instanceof NullPointerException) {
                // @TODO handle some other way
                System.exit(1);
            }
        }
    }

    private void travelTimeAndBudget() {
        inputTravelDays(INPUT_PROMPT_TRAVEL_DAYS);
        inputTravelBudget(INPUT_PROMPT_TRAVEL_BUDGET);
        inputDestinationCurrency(INPUT_PROMPT_CURRENCY_ALPHA3);
    }

    void timeDifference() {
    }

    void countryArea() {
    }

    void round() {
    }

    void bonus() {
    }
}
