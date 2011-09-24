package bank.main;

import java.sql.DriverManager;
import java.sql.Connection;

/**
 * @author acb1485
 * @version 1.0
 */
public class BankMain {

	// Initialize MySQL DB information into class variables
	private static final String url = "jdbc:mysql://mentok.se.rit.edu:3306/acb1485";
	private static final String user = "acb1485";
	private static final String password = "Eigh7fiung";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Bank application started.");
		
		Connection con = null;
		try {
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			con = DriverManager.getConnection( url, user, password );
			System.out.println( "Connection was successfully established." );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

}
