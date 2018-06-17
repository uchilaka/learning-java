package org.uchechilaka.HelloWorld;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    String expenseSummary;
    Double timeDifferenceInHours;
    //    String homeTimeZone;
//    String destinationTimeZone;
    String destinationName;
    Double destinationAreaInSqKM;

    String getExchangeRatePrompt() throws Exception {
        if (destinationCurrencyAlpha3 == null) {
            throw new MissingOrInvalidPropertyException("destinationCurrencyAlpha3", "Required property destinationCurrencyAlpha3 MUST be set before you can use this prompt helper function");
        }
        return "How many " + destinationCurrencyAlpha3.toUpperCase() + " are there in 1 USD? ";
    }

    String getFirstName() {
        //return names.length > 1 ? (firstName == null ? names[1] : firstName) : names[0];
        return names[0];
    }

    String getLastName() {
        return names[names.length - 1];
    }

    Double calcDailyBudget(Boolean formatted) {
        // @IMPORTANT do NOT use Math.round! This is because the consideration is for currency, and also not the requirement
        Double dailyBudget = travelBudgetInDollars / (travelHoursAvailable / 24.0);
//        long cleanDailyBudget = Math.round(dailyBudget * 100);
        if(formatted) {
            long cleanDailyBudget = (int) (dailyBudget * 100);
            return cleanDailyBudget / 100.0;
        } else {
            return dailyBudget;
        }
    }

    Double calcTotalBudgetInDestinationCurrency() {
        Double totalBudget = calcDailyBudget(false) * (travelHoursAvailable / 24.0) * destinationExchangeRateToUSD;
        return (int) (totalBudget * 100.0) / 100.0;
    }

    Double calcDailyBudgetInDestinationCurrency() {
        Double totalBudget = calcDailyBudget(false) * destinationExchangeRateToUSD;
        return (int) (totalBudget * 100.0) / 100.0;
    }

//    String getExampleDestinationTimeAtLocalMidnight() {
//        Double diffInMinutes = Math.floor(timeDifferenceInHours * 60);
//        int minuteOfHour = (int)(Math.abs(diffInMinutes) % 60);
//        int dayDiffAtDest = (int)(timeDifferenceInHours / 24.0);
//        int hoursInSameDay = (int)(timeDifferenceInHours - (dayDiffAtDest * 24.0));
//        int hourOfDay = (int)(timeDifferenceInHours > 0 ? 12 % timeDifferenceInHours.intValue() : (24.0 + timeDifferenceInHours));
//        return hourOfDay + ":" + minuteOfHour;
//    }

    String getExampleDestinationTimeAtLocalHourMinute(int localHour, int localMinute) {
        // Some notes: https://docs.oracle.com/javase/tutorial/datetime/iso/timezones.html
//        DateTimeFormatter formatWithTimeZone = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a Z");
        long offsetInHours = timeDifferenceInHours.longValue();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localMidnight = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), localHour, localMinute);
        ZonedDateTime localMidnightZoned = ZonedDateTime.of(localMidnight, ZoneId.of("America/New_York"));
        ZonedDateTime destinationZoned = offsetInHours > 0 ? localMidnightZoned.plusHours(offsetInHours) : localMidnightZoned.minusHours(offsetInHours);
        return destinationZoned.format(format);
    }

    String getExampleDestinationTimeAtLocalMidnight() {
        return getExampleDestinationTimeAtLocalHourMinute(0, 0);
    }

    Double calcDestinationAreaInSqMI() throws MissingOrInvalidPropertyException {
        if(destinationAreaInSqKM == null) throw new MissingOrInvalidPropertyException("The TravelGuest property destinationAreaInSqKM MUST be populated before using calcDestinationAreaInSqMI");
        return destinationAreaInSqKM * 0.38610;
    }
}