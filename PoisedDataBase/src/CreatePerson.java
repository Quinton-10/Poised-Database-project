
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * CreatePerson is a subclass that inherits methods from the CheckInput
 * superclass.
 * <p>
 * This class contains variables and a method for creating a person object to
 * Poised . The Poised class, which runs the main program, calls on methods from
 * this class to create a person.
 * 
 * @author Quinton Amos
 */
public class CreatePerson extends CheckInput {
	private String relation;
	private String name;
	private String tellNum;
	private String email;
	private String physicalAddress;

	/**
	 * The newPerson method is used to create a new person object to Poised.
	 * <p>
	 * It asks the user to enter information related to the persons details which is
	 * then verified with methods from the superclass CheckInput, and stored in
	 * variables related to the person object. It displays the new person object
	 * created and saves it to database 'PoisePMS' table: 'persons'
	 */
	public void newPerson() {
		// asks user to enter the persons details
		System.out.println("Enter Relation: (Example: Customer, architect, etc.)");
		relation = checkString("relation");
		System.out.println("ENTER Full name: ");
		name = checkString("name");
		System.out.println("ENTER email address: ");
		email = checkString("email");
		System.out.println("ENTER Physical Address: ");
		physicalAddress = checkString("physicalAddress");
		System.out.println("ENTER Tellephone Number: ");
		tellNum = checkString("tellNum");
		try (Connection connection = DriverManager.getConnection( // making a connection to the database
				"jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "swordfish");
				PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO persons (relation, name, email, tellNum, physicalAddress) VALUES (?, ?, ?, ?,?)");) {
			statement.setString(1, relation);
			statement.setString(2, name);
			statement.setString(3, email);
			statement.setString(4, tellNum);
			statement.setString(5, physicalAddress);
			int rows = statement.executeUpdate();
			System.out.println(name + " successfully saved to database " + rows + " rows Affected"); // success message displayed
			

			statement.close(); // close parsedStatement
			connection.close(); // close the Connection

		} catch (SQLException e) {
			System.out.println("ERROR while communicating with database");
		}
	}
}