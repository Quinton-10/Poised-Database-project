import java.util.Scanner;
/**
 * CheckInput is the Poised Project superclass.
 * <p>
 * This class contains three check methods for different user inputs. These
 * methods are inherited by the three subclasses of the program to ensure all
 * user input is correctly checked.
 * 
 * @author Quinton Amos
 */
public class CheckInput {
	/**
	 * This method verifies user input when asked to enter a String
	 * 
	 * @param type type describes the input required for the user to enter
	 * @return returns the verified output String
	 */

	public static String checkString(String type) {
		while (true) {
			Scanner input = new Scanner(System.in);
			String userInput = input.nextLine();

			if ((userInput == null) || (userInput.length() > 150)) {
				System.out.println("Invalid entry Please try again: ");

			} else {
				return userInput;

			}
		}
	}

	/**
	 * This method verifies user input when asked to enter an integer
	 * 
	 * @param type type describes the input required for the user to enter
	 * @return returns the verified output integer
	 */
	public static int intCheck(String type) {
		while (true) { // will loop the whole time till user enters correct format
			Scanner intInput = new Scanner(System.in);
			String numbers = intInput.nextLine();

			try {
				int outputInt = Integer.parseInt(numbers);
				return outputInt;
			} catch (NumberFormatException ex) {
				System.out.println("Incorrect entry. Please re-enter the " + type + ": \n"); // Error message displayed
																								// if parsing is not
																								// possible.

			}
		}
	}

	/**
	 * This method verifies user input when asked to enter a Double
	 * 
	 * @param type type describes the input required for the user to enter
	 * @return returns the verified output Double
	 */
	public static double doubleCheck(String type) {
		while (true) {
			Scanner doubleInput = new Scanner(System.in);
			String doubleNum = doubleInput.nextLine();

			try {
				double outputDouble = Double.parseDouble(doubleNum);

				return outputDouble;

			} catch (NumberFormatException ex) {
				System.out.println("Incorrect entry. Please re-enter: "); // Error message displayed if parsing is not
																			// possible.

			}
		}
	}

}


