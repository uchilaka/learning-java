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

class VacationPlanner {

    private static String INPUT_PROMPT_NAME = "what's your name? ";
    private static String INPUT_PROMPT_TRAVEL_DAYS = "How many days are you going to spend traveling? ";
    private static String INPUT_PROMPT_TRAVEL_BUDGET = "How much money, in USD, are you planning to spend on your trip? ";
    private static String INPUT_PROMPT_CURRENCY_ALPHA3 = "What is the three letter currency symbol for your travel destination? ";
    private static String INPUT_PROMPT_TIME_DIFF = "What is the time difference, in hours, between your home and your destination? ";
    private static String INPUT_PROMPT_SQ_AREA = "What is the square area of your destination country in KM²? ";
    static String REGEX_STRING_NEGATIVE_INPUT = "(?i:no?.*)";
    static String REGEX_STRING_AFFIRMATIVE_INPUT = "(?i:y(es)?)";

    private Scanner input;
    private TravelGuest currentGuest;

    VacationPlanner() {
        input = new Scanner(System.in);
        currentGuest = new TravelGuest();
    }

    private void confirmExit() throws RePromptRequiredException {
        System.out.println("Would you like to quit? (Enter N to continue, or just Enter to confirm) ");
        String response = input.nextLine();
        if (response.isEmpty() || response.matches(REGEX_STRING_AFFIRMATIVE_INPUT)) {
            input.close();
            System.exit(0);
        }
        // Still running
        throw new RePromptRequiredException();
    }

    private void captureNames(String prompt) {
        System.out.print(prompt);
        captureNames();
    }

    private void captureNames() {
        try {
            String fullName = input.nextLine();
            if (fullName.isEmpty()) {
                confirmExit();
            } else {
                currentGuest.names = fullName.split("\\s+");
                if (currentGuest.names.length < 2) {
                    System.out.print("To verify, your last name is " + currentGuest.getLastName() + ", is that correct (Enter N if not, or just Enter to confirm)? ");
                    String response = input.nextLine();
//                System.out.println("PSST: Found input!");
                    if (response.matches(REGEX_STRING_NEGATIVE_INPUT)) {
                        throw new Exception("Last name not entered as instructed");
                    }
                }
            }
            // all set!
        } catch (Exception e) {
            if (e instanceof RePromptRequiredException) {
                captureNames(INPUT_PROMPT_NAME);
            } else if (e instanceof NullPointerException) {
                // @TODO handle some other way
                System.exit(1);
            } else {
                captureNames("Looks like we got an incorrect or invalid name. Let's try again - " + INPUT_PROMPT_NAME);
            }
        }
    }

    private void sectionBreak() {
        System.out.println();
        System.out.println("************"); // used 12 stars instead of 11 in the example output (symmetry)
        System.out.println();
    }

    private void greet() {
        captureNames("Hi! To get started, " + INPUT_PROMPT_NAME);
        System.out.print("Nice to meet you, " + currentGuest.getFirstName() + "! Where are you traveling to? ");
        currentGuest.destinationName = input.nextLine();
        System.out.print("Great! " + currentGuest.destinationName + " sounds like a great trip :)");
        // sectionBreak();
    }

    private void inputTravelDays(String prompt) {
        System.out.print(prompt);
        inputTravelDays();
    }

    private void inputTravelDays() {
        try {
            Double days = input.nextDouble();
            // @TODO calculate travel hours
            currentGuest.travelHoursAvailable = days * 24.0;
        } catch (Exception e) {
            // Some notes on this: https://softwareengineering.stackexchange.com/a/271877
            if (e instanceof InputMismatchException) {
                inputTravelDays("Looks like you input an invalid number of days. Let's try again - " + INPUT_PROMPT_TRAVEL_DAYS);
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
            currentGuest.travelBudgetInDollars = input.nextDouble();
        } catch (Exception e) {
            if (e instanceof InputMismatchException) {
                inputTravelBudget("Invalid travel budget amount (must be a number, like 2400). Let's try again - " + INPUT_PROMPT_TRAVEL_BUDGET);
            } else if (e instanceof NullPointerException) {
                throw e;
            }
        }
    }


    private void inputDestinationCurrency(String prompt) {
        System.out.print(prompt);
        inputDestinationCurrency();
    }

    private void inputDestinationCurrency() {
        try {
            currentGuest.destinationCurrencyAlpha3 = input.next().toUpperCase();
            if (currentGuest.destinationCurrencyAlpha3.isEmpty())
                throw new MissingOrInvalidPropertyException("You MUST provide a 3 letter string for the currency symbol");
            System.out.print(currentGuest.getExchangeRatePrompt());
            if (!input.hasNextDouble())
                throw new InputMismatchException("Exchange rate to the USD must be a number, like 5.45 (which would mean 1 USD = 5.45 " + currentGuest.destinationCurrencyAlpha3 + ")");
            currentGuest.destinationExchangeRateToUSD = input.nextDouble();
        } catch (Exception e) {
            if (e instanceof InputMismatchException) {
                inputDestinationCurrency("Invalid input (must be 3 letter string). Let's try again - " + INPUT_PROMPT_CURRENCY_ALPHA3);
            } else if (e instanceof MissingOrInvalidPropertyException) {
                try {
                    confirmExit();
                } catch (RePromptRequiredException e2) {
                    inputDestinationCurrency(INPUT_PROMPT_CURRENCY_ALPHA3);
                }
            } else if (e instanceof NullPointerException) {
                // @TODO handle some other way
                System.exit(1);
            } else {
                inputDestinationCurrency("Invalid input. Let's try again - " + INPUT_PROMPT_CURRENCY_ALPHA3);
            }
        }
    }

    private void travelTimeAndBudget() {
        inputTravelDays(INPUT_PROMPT_TRAVEL_DAYS);
        inputTravelBudget(INPUT_PROMPT_TRAVEL_BUDGET);
        inputDestinationCurrency(INPUT_PROMPT_CURRENCY_ALPHA3);
    }

    private String getExpenseSummary() {
        String currency = currentGuest.destinationCurrencyAlpha3.toUpperCase();
        int travelDays = (int) (currentGuest.travelHoursAvailable / 24.0);
        String dayStatement = "If you are traveling for " + travelDays
                + " days that is the same as " + currentGuest.travelHoursAvailable.intValue() + " hours or "
                + (int) (currentGuest.travelHoursAvailable * 60.0) + " minutes";
        String budgetStatement = "If you are going to spend $" + currentGuest.travelBudgetInDollars.intValue()
                + " USD that means per day you can spend up to $" + currentGuest.calcDailyBudget(true) + " USD"
                + "\nYour total budget in " + currency + " is " + currentGuest.calcTotalBudgetInDestinationCurrency() + " " + currency
                + ", which per day is " + currentGuest.calcDailyBudgetInDestinationCurrency() + " " + currency;
        return dayStatement + "\n" + budgetStatement;
    }

    private void calcExpenseSummary() {
        currentGuest.expenseSummary = getExpenseSummary();
        System.out.println();
        System.out.print(currentGuest.expenseSummary);
    }

    private void timeDifference() {
        try {
            currentGuest.timeDifferenceInHours = input.nextDouble();
            System.out.print("That means that when it is midnight at home it will be "
                    + currentGuest.getExampleDestinationTimeAtLocalMidnight() + " in your travel destination "
                    + "and when it is noon at home it will be " + currentGuest.getExampleDestinationTimeAtLocalHourMinute(12, 0));
        } catch (Exception e) {
            timeDifference("Invalid input. Let's try again - " + INPUT_PROMPT_TIME_DIFF);
        }
    }

    private void timeDifference(String prompt) {
        System.out.print(prompt);
        timeDifference();
    }

    // @TODO figure out haversine for bonus problem: https://en.wikipedia.org/wiki/Haversine_formula
//    private Double haversine(Double point) {
//        return (1 - Math.cos(point)) / 2.0;
//    }
//
//    private Double haversine(Double pointA, Double pointB) {
//
//    }

    private void countryArea() {
        try {
            currentGuest.destinationAreaInSqKM= input.nextDouble();
            Double inSqMI = Math.floor(currentGuest.calcDestinationAreaInSqMI() * 100) / 100;
            System.out.print("In MI² that is " + inSqMI.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            countryArea("Invalid input. Let's try again - " + INPUT_PROMPT_SQ_AREA);
        }
    }

    private void countryArea(String prompt) {
        System.out.print(prompt);
        countryArea();
    }

//    void round() {
//    }
//
//    void bonus() {
//    }

    void begin() {
        greet();
        sectionBreak();
        travelTimeAndBudget();
        calcExpenseSummary();
        sectionBreak();
        timeDifference(INPUT_PROMPT_TIME_DIFF);
        sectionBreak();
        countryArea(INPUT_PROMPT_SQ_AREA);
        sectionBreak();
    }
}
