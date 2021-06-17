
import java.sql.*;
import java.sql.Date;

/**
 * SavedProjects is a subclass that inherits methods from the InputChecks
 * superclass.
 * <p>
 * This class contains methods to find, view, update, finalise, check if
 * incomplete and check overdue on projects in Poised. The Poised class, which
 * runs the main program, calls on methods from this class to perform various
 * functions on projects managed by Poised.
 * 
 * @author Quinton Amos
 */
public class SavedProjects extends CheckInput {
	/**
	 * The newProject method is used to add a new project object to Poised.
	 * <p>
	 * It asks the user to enter information related to project details which is
	 * then verified with methods from the superclass InputChecks, and stored in
	 * variables related to the project object. It is then saved to Database
	 * PoisePMS in table called projects
	 */
	public void newProject() {
		// complete and finalised set to "False" and "not finalised"
		String complete = "False";
		String finalised = "not finalised";

		System.out.print("Enter Project Number: ");
		int projectNum = intCheck("Project Number");

		System.out.print("\nEnter the Total fee for the project: ");
		double totalFee = doubleCheck("Total fee");

		System.out.print("\nEnter amount paid to date: ");
		double amountPaid = doubleCheck("Amount Paid");

		System.out.print("\nEnter the Deadline for the project(yyyy-mm-dd): ");
		String dueDate = checkString("Due Date");

		System.out.print("\nEnter ERF number: ");
		int erf = intCheck("ERF Number");

		System.out.print("\nEnter Clients Surname: ");
		String clientSurname = checkString("Surname");

		System.out.print("\nEnter Project name: ");
		String projectName = checkString("Project Name");

		System.out.print("\nEnter the building type(house, appartment, office etc.): ");
		String buildingType = checkString("Building type");

		System.out.print("\nEnter Physical Address: ");
		String address = checkString("Physical Address");
		// if there is not a project name it will create a project name with the clients
		// surname and the building type
		if (projectName == "") {
			projectName = buildingType + " " + clientSurname;
			System.out.println("New Project Details: \n" + "Project Number: " + projectNum + "\nProjectName: "
					+ projectName + "\nBuilding Type: " + buildingType + "\nPhysical Address: " + address
					+ "\nERF Number: " + erf + "\nTotal Fee: R" + totalFee + "\nAmount Paid: R" + amountPaid
					+ "\nDue Date: " + dueDate + "\nCompletion Date: " + complete + "\nProject Status: " + finalised);

			// else it will print the new project that they created
		} else {
			System.out.println("Finalised Project Details: \n" + "Project Number: " + projectNum + "\nProject Name: "
					+ projectName + "\nBuilding Type: " + buildingType + "\nPhysical Address: " + address
					+ "\nERF Number: " + erf + "\nTotal Fee: R" + totalFee + "\nAmount Paid: R" + amountPaid
					+ "\nDue Date: " + dueDate + "\nCompletion Date: " + complete + "\nProject Status: " + finalised);
		}
		// Connecting to database server and then saving the inputted info to the
		// specified table
		try (Connection connection = DriverManager.getConnection( // making a connection to the database
				"jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
				PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO projects (projectNum, projectName, buildingType, address, erf, totalFee, amountPaid, dueDate, customerSurname, complete, finalised) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");) {
			statement.setInt(1, projectNum);
			statement.setString(2, projectName);
			statement.setString(3, buildingType);
			statement.setString(4, address);
			statement.setInt(5, erf);
			statement.setDouble(6, totalFee);
			statement.setDouble(7, amountPaid);
			statement.setString(8, dueDate);
			statement.setString(9, clientSurname);
			statement.setString(10, complete);
			statement.setString(11, finalised);
			int rows = statement.executeUpdate();
			System.out.println("Project successfully saved to database " + rows + " rows Affected");

			statement.close(); // close parsedStatement
			connection.close(); // close the Connection

		} catch (SQLException e) {
			System.out.println("ERROR while communicating with database");

		}

	}

	/**
	 * The searchProject method runs through the PoisePMS database to find a certain
	 * project object needed.
	 * <p>
	 * 
	 * @throws ClassNotFoundException is thrown when the Java Virtual Machine (JVM)
	 *                                tries to load a particular class and the
	 *                                specified class cannot be found in the
	 *                                classpath.
	 */
	public int searchProject() throws ClassNotFoundException {
		ResultSet results;

		System.out.println("Enter Project Number you want to search: ");
		int search = intCheck("projectNum");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
					"otheruser", "swordfish");
			PreparedStatement statement = connection.prepareStatement("SELECT *  FROM projects WHERE projectNum=?");
			statement.setInt(1, search);
			results = statement.executeQuery();
			while (results.next()) {
				System.out.println("Project Number: \t" + results.getInt("projectNum") + "\nProject Name: \t"
						+ results.getString("projectName") + "\nBuilding Type: \t" + results.getString("buildingType")
						+ "\nPhysical Address: " + results.getString("address") + "\nERF Number: \t"
						+ results.getString("erf") + "\nTotal Fee: \tR" + results.getFloat("totalFee")
						+ "\nAmount Paid: \t" + results.getFloat("amountPaid") + "\nDue Date: \t"
						+ results.getString("dueDate") + "\nCustomer Surname: \t" + results.getString("customerSurname")
						+ "\nFinalised: \t" + results.getString("finalised") + "\nCompletion Date: "
						+ results.getString("complete") + "\n");

			}
			results.close(); // close ResultSet
			statement.close(); // close parsedStatement
			connection.close(); // close the Connection

		} catch (SQLException e) {
			System.out.println(e);
		}
		return search;
	}

	/**
	 * The viewProjects method runs through the projects table to view all projects
	 * listed.
	 * <p>
	 * The projects are each displayed in an easy-to-read format.
	 */

	public void viewProject() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");

			String sql = "SELECT * FROM projects";
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				System.out.println("Project Number: \t" + results.getInt("projectNum") + "\nProject Name: \t"
						+ results.getString("projectName") + "\nBuilding Type: \t" + results.getString("buildingType")
						+ "\nPhysical Address: " + results.getString("address") + "\nERF Number: \t"
						+ results.getString("erf") + "\nTotal Fee: \tR" + results.getFloat("totalFee")
						+ "\nAmount Paid: \t" + results.getFloat("amountPaid") + "\nDue Date: \t"
						+ results.getString("dueDate") + "\nCustomer Surname: \t" + results.getString("customerSurname")
						+ "\nFinalised: \t" + results.getString("finalised") + "\nCompletion Date: "
						+ results.getString("complete") + "\n");
			}
		} catch (Exception e) {
			System.out.println("ERROR occured: " + e);

		}
	}

	/**
	 * The editProject method is used to update either the due date or the amount
	 * paid to date.
	 */
	public void editProject() {
		// Ask user to input the project number they would like to update
		// Then is checked if input is correct
		System.out.println("Please enter Project number of the project that you which you would like to edit: \n ");
		int projectNum = intCheck("project number");
		// then menu pops up to ask user what they would like to update
		System.out.println(
				"What would you like to edit: " + "\n1 : Edit Due Date" + "\n2 : Edit total fees paid to date");
		int option = intCheck("option");

		// If user enter "1" they can update the due date and is the updated in the
		// database PoisePMS table projects
		if (option == 1) {
			System.out.println("Enter new Due Date: ");
			String newDueDate = checkString("Due Date");
			try (Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
					PreparedStatement statement = connection
							.prepareStatement("UPDATE projects SET dueDate=? WHERE projectNum = ?");) {
				statement.setString(1, newDueDate);
				statement.setInt(2, projectNum);
				int rows = statement.executeUpdate();
				// when it is successfully saved updated to the database message is displayed
				System.out.println("updated succesfully to database " + rows + " affected");

				statement.close(); // close parsedStatement
				connection.close(); // close the Connection
			} catch (SQLException e) {

			}

			// if user enters "2" the can edit the amount paid to date
		} else if (option == 2) {
			System.out.println("Enter new Total Amount paid to date: ");
			double newAmountPaid = doubleCheck("Total Fee");
			try (Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
					PreparedStatement statement = connection
							.prepareStatement("UPDATE projects SET amountPaid=? WHERE projectNum= ?");) {
				statement.setDouble(1, newAmountPaid);
				statement.setInt(2, projectNum);
				int rows = statement.executeUpdate();
				// when it is successfully saved updated to the database message is displayed
				System.out.println("updated succesfully " + rows + " affected");

				statement.close(); // close parsedStatement
				connection.close(); // close the Connection

			} catch (SQLException e) { // exception is thrown
				System.out.println("ERROR while communicating with database");
			}
		}
	}

	/**
	 * The finalising method finalises a project object by generating an invoice,
	 * marking the project as finalised and adding a completion date to the project
	 * info.
	 * <p>
	 * It selects a project object from project table database PoisePMS, checks
	 * whether an invoice must be generated and then marks the project as finalised,
	 * and adds a completion date. The finalised project is then updated and saved
	 * to projects table
	 * 
	 */
	public void finalising() throws ClassNotFoundException {

		System.out.println("Please enter the project number or name of the project you wish to finalise: \n");
		int projectNum = intCheck("project number");
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
				"otheruser", "swordfish");
				Statement statement = connection.createStatement();
				ResultSet results = statement
						.executeQuery("SELECT totalFee, amountPaid FROM projects WHERE projectNum = " + projectNum);) {
			double totalFee = 0;
			double amountPaid = 0;

			while (results.next()) {
				totalFee = results.getDouble("totalFee");
				amountPaid = results.getDouble("amountPaid");

			}
			if (totalFee == amountPaid) {
				System.out.println("This project has already been paid in full. No invoice to be generated.");

				System.out.println("Please add a completion date for the project: ");
				String complete = checkString("completion date");
				String finalised = "Finalised";
				PreparedStatement newStatement = connection
						.prepareStatement("UPDATE projects SET complete=?  WHERE projectNum = ?");
				newStatement.setString(1, complete);
				newStatement.setInt(2, projectNum);
				PreparedStatement finalStatement = connection
						.prepareStatement("UPDATE projects SET finalised=?  WHERE projectNum = ?");
				finalStatement.setString(1, finalised);
				finalStatement.setInt(2, projectNum);
				int rows = newStatement.executeUpdate();
				// when it is successfully saved updated to the database message is displayed
				System.out.println("Finalised succesfully ");

			} else if (totalFee != amountPaid) {
				System.out.println("There is Still an outstanding amount\n");
				System.out.println("Enter Customers Full name to Create an invoice: ");
				String name = checkString("name");
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection2 = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
				PreparedStatement newStatement = connection2
						.prepareStatement("SELECT * FROM persons WHERE relation= ? AND name=? ");
				newStatement.setString(1, "Customer");
				newStatement.setString(2, name);
				ResultSet results2 = newStatement.executeQuery();
				while (results2.next()) {
					double outstanding = totalFee - amountPaid;
					String customerName = results2.getString("name");
					String email = results2.getString("email");
					String tellNum = results2.getString("tellNum");
					System.out.println("\nCustomer Name: " + customerName + "\nEmail: " + email + "\nQuantity: "
							+ tellNum + "\nAmount OutSstanding: R" + outstanding);

					System.out.println("Please add a completion date for the project: ");
					String complete = checkString("complete");
					String finalised = "Finalised";

					Connection connection3 = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
					PreparedStatement statement3 = connection3
							.prepareStatement("UPDATE projects SET complete=? WHERE projectNum = ?");
					statement3.setString(1, complete);
					statement3.setInt(2, projectNum);
					PreparedStatement statement4 = connection3
							.prepareStatement("UPDATE projects SET finalised=? WHERE projectNum = ?");
					statement4.setString(1, finalised);
					statement4.setInt(2, projectNum);
					statement3.executeUpdate();
					statement4.executeUpdate();
					// when it is successfully saved updated to the database message is displayed
					System.out.println("Finalised succesfully ");

					statement3.close(); // close parsedStatement
					connection3.close(); // close the Connection

				}
				results.close(); // close ResultSet
				newStatement.close(); // close parsedStatement
				connection2.close(); // close the Connection

			}
		} catch (SQLException e) {
			System.out.print(e);

		}

	}

	/**
	 * The viewIncomplete checks the database if there are any incomplete tasks
	 * <p>
	 * 
	 * @throws ClassNotFoundException is thrown when the Java Virtual Machine (JVM)
	 *                                tries to load a particular class and the
	 *                                specified class cannot be found in the
	 *                                classpath.
	 */
	public void viewIncomplete() throws ClassNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");

			String sql = "SELECT * FROM projects WHERE complete = 'False'";
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();
			// displays all incomplete projects in a readable format
			while (results.next()) {
				System.out.println("Project Number: \t" + results.getInt("projectNum") + "\nProject Name: \t"
						+ results.getString("projectName") + "\nBuilding Type: \t" + results.getString("buildingType")
						+ "\nPhysical Address: " + results.getString("address") + "\nERF Number: \t"
						+ results.getString("erf") + "\nTotal Fee: \tR" + results.getFloat("totalFee")
						+ "\nAmount Paid: \t" + results.getFloat("amountPaid") + "\nDue Date: \t"
						+ results.getDate("dueDate") + "\nCustomer Surname: \t" + results.getString("customerSurname")
						+ "\nFinalised: \t" + results.getString("finalised") + "\nCompletion Date: "
						+ results.getString("complete") + "\n");
			}
		} catch (Exception e) {
			System.out.println("ERROR occured: " + e);

		}
	}

	/**
	 * The viewOverdue checks the database if there are any overdue tasks
	 * <p>
	 * 
	 * @throws ClassNotFoundException is thrown when the Java Virtual Machine (JVM)
	 *                                tries to load a particular class and the
	 *                                specified class cannot be found in the
	 *                                classpath.
	 */

	public void viewOverdue() throws ClassNotFoundException {
		// Gets todays date and saves it in an Date variable
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");
			// the SQL statement checks if the todays date is past the dueDate
			String sql = "SELECT * FROM projects WHERE dueDate < ?  AND complete = 'False'";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, date);
			results = statement.executeQuery();
			// if it is overdue it is displayed in a readable format
			while (results.next()) {
				System.out.println("Project Number: \t" + results.getInt("projectNum") + "\nProject Name: \t"
						+ results.getString("projectName") + "\nBuilding Type: \t" + results.getString("buildingType")
						+ "\nPhysical Address: " + results.getString("address") + "\nERF Number: \t"
						+ results.getString("erf") + "\nTotal Fee: \tR" + results.getFloat("totalFee")
						+ "\nAmount Paid: \t" + results.getFloat("amountPaid") + "\nDue Date: \t"
						+ results.getDate("dueDate") + "\nCustomer Surname: \t" + results.getString("customerSurname")
						+ "\nFinalised: \t" + results.getString("finalised") + "\nCompletion Date: "
						+ results.getString("complete") + "\n");
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
		

	

