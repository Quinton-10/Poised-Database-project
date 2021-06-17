
/**
 * PoisedMain is a subclass that inherits methods from the CheckInput
 * superclass.
 * <p>
 * This class runs the main program method and calls other methods from the
 * CreatePerson and SavedProjects classes. It displays a menu to the user with
 * options for the Poised Management System.
 * 
 * @author Quinton Amos
 */
public class PoisedMain extends CheckInput {
	/**
	 * This is the main method which runs the program.
	 * <p>
	 * 
	 * @param args java command line arguments
	 * @throws ClassNotFoundException is thrown when the Java Virtual Machine (JVM)
	 *                                tries to load a particular class and the
	 *                                specified class cannot be found in the
	 *                                classpath.
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		/*
		 * A welcome message is displayed to the user.
		 */
		System.out.println("Welcome to Poised!");

		/*
		 * A menu pops up displaying different options. A number of methods from the
		 * CreatePerson and SavedProjects classes are called on, depending on the user's
		 * selected option. For example, the newProject() method from SavedProject class
		 * is called on for option '2'. The user will return to the menu after each
		 * option, unless they enter "99" to exit the program.
		 */
		while (true) {
			System.out.println("\nPlease Enter a number from menu below: " + "\n1 : View all created Projects"
					+ "\n2 : Create a New Project" + "\n3 : Edit Existing Project Details" + "\n4 : Finalize a Project"
					+ "\n5 : View all Incomplete Projects" + "\n6 : View all Overdue Projects" + "\n7 : Find a Project"
					+ "\n8 : Create and Save Person" + "\n99: Exit");

			int userInput = intCheck("menu option"); // Checking if user entered an integer from the PoisedInputChecks
			// class.

			if (userInput == 1) { // If user enters "1" they can view all projects in
				// "projects.txt"
				SavedProjects view = new SavedProjects();
				view.viewProject();
				//
			} else if (userInput == 2) { // If user enter "2" they can create a new project
				SavedProjects create = new SavedProjects();
				create.newProject();

			} else if (userInput == 3) { // if user enters "3" they can edit a project of their
				// choosing
				SavedProjects edit = new SavedProjects();
				edit.editProject();

			} else if (userInput == 4) { // If user enters "4" the can finalize a project
				SavedProjects finalise = new SavedProjects();
				finalise.finalising();

			} else if (userInput == 5) { // If user enters "5" they can view all in complete
				// task that are not over due
				SavedProjects incomplete = new SavedProjects();
				incomplete.viewIncomplete();

			} else if (userInput == 6) {// if user enters "6" they can view all overdue tasks
				SavedProjects overDue = new SavedProjects();
				overDue.viewOverdue();

			} else if (userInput == 7) {// if user enters "7" they can search for a project

				SavedProjects search = new SavedProjects();
				search.searchProject();

			} else if (userInput == 8) { // if user enters "8" they can create a person
				CreatePerson create = new CreatePerson();
				create.newPerson();

			} else if (userInput == 99) { // If user enters "99" they can exit the program.
				System.out.println("Good Bye");
				break;
			}

		}
	}
}




	
