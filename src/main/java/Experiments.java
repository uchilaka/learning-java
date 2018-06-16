import java.util.InputMismatchException;
import java.util.Scanner;
class Experiments {
    private Double aNumber;
     void illustratingNumbers() {
        if(aNumber == null) aNumber = 473.25;
        // Print the double number
        System.out.println("Double number: " + aNumber.toString());
        // Print an int
        System.out.println("Int of number: " + aNumber.intValue());
        // Print with cast
        System.out.println("Cast int of number: " + (int)aNumber.doubleValue());
    }

    // @TODO update to handle more exceptions than just InputMismatch
    // @TODO update to test or more exceptions than just InputMismatch
    void illustrateCapturingInput() {
         try {
             Scanner input = new Scanner(System.in);
             System.out.print("How old are you? ");
             Double age = input.nextDouble();
             System.out.println("Got it! You are " + age + " years old");
         } catch(InputMismatchException e) {
             System.out.println("There was a problem with your input. Try again?");
             illustrateCapturingInput();
         }
    }
}
