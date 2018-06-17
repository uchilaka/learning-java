package org.uchechilaka.HelloWorld;

import java.util.Scanner;

/**
 * Class for managing travel guest data
 */
class TravelGuest {
    TravelGuest() {
        // @TODO do constructor stuff
    }

    String[] names;
    String firstName;
    String lastName;
    String cityAlpha3;
    Double travelHoursAvailable;
    Double travelBudgetInDollars;
    String homeTimeZone;
    String destinationTimeZone;
    String destinationName;

    String getFirstName() {
        return names.length > 1 ? (firstName == null ? names[1] : firstName) : names[0];
    }

    String getLastName() {
        return names[0];
    }
}

public class VacationPlanner {

    private static String NEGATIVE_INPUT_REGEX_STRING = "(?i:no?.*)";
    private static String AFFIRMATIVE_INPUT_REGEX_STRING = "(?i:y(es)?)";

    private Scanner input;
    private TravelGuest guest;

    VacationPlanner() {
        input = new Scanner(System.in);
        guest = new TravelGuest();
    }

    public void begin() {
        greet();
    }

    private void captureNames() {
        try {
            guest.names = input.nextLine().split("\\s+");
            System.out.print("To verify, your last name is " + guest.getLastName() + ", is that correct (Enter N if not, or just Enter to confirm)? ");
            String response = input.nextLine();
            if(!response.isEmpty()) {
//                System.out.println("PSST: Found input!");
                if (response.matches(NEGATIVE_INPUT_REGEX_STRING)) {
                    throw new Exception("Last name not entered as instructed");
                }
            }
            // all set!
        } catch (Exception e) {
            System.out.println("Looks like we got an incorrect or invalid name. Try again?");
            captureNames();
        }
    }

    void greet() {
        System.out.print("Hi! To get started, what's your name (starting with your last name)? ");
        captureNames();
        System.out.print("Nice to meet you, " + guest.getFirstName() + "! Where are you traveling to? ");
        guest.destinationName = input.next();
        System.out.print("Great! " + guest.destinationName + " sounds like a great trip :)");
    }

    void travelTimeAndBudget() {
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
