package org.uchechilaka.HelloWorld;

public class App {
    public static void doSomeUselessStuff() {
        int x = 10;
        int y = 5;
        int z = x + y;
        x = z - y;
        y = x + z;
        z = 2 * z;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("z = " + z);
    }
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

//        System.out.println("10 plus 20 is " + 10 + 20);
//        System.out.println("1 + 3 / 2 - 7 % 3 = " + ( ((3/2) - (7 % 3)) + 1 ));
////        System.out.println("1 + 3 / 2 - 7 % 3 = " + (((3/2) + 1 - 7) % 3));
//        System.out.println("(Computed) = " + (1 + 3 / 2 - 7 % 3));

        doSomeUselessStuff();

//        VacationPlanner agency = new VacationPlanner();
//        agency.begin();

    }
}
