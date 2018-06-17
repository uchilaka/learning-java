package org.uchechilaka.HelloWorld;

public class App {
    public static void main(String[] args) {
        // System.out.println("Hello World!");
//        Experiments exp = new Experiments();
//        exp.illustrateCapturingInput();
//        exp.illustratingNumbers();


//        TravelGuest guest = new TravelGuest();
//        guest.destinationAreaInSqKM = 1973000 * 1.0;
//        try {
//            System.out.println("Calc destination in MI²: " + guest.calcDestinationAreaInSqMI().toString());
//        } catch(Exception ex) {
//            System.out.println(ex.toString());
//        }

        VacationPlanner agency = new VacationPlanner();
        agency.begin();

    }
}
