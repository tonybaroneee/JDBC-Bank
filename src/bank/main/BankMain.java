package bank.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author acb1485
 * @version 1.0
 */
public class BankMain {

	// Initialize MySQL DB information into class variables.
	private static final String url = "jdbc:mysql://mentok.se.rit.edu:3306/acb1485";
	private static final String user = "acb1485";
	private static final String password = "Eigh7fiung";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( "Bank application started." );

		// Initialize our JDBC variables.
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			// Make the initial connection with our database.
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			con = DriverManager.getConnection( url, user, password );
			System.out.println( "Connection was successfully established." );

			// Construct our query then execute it, storing the ResultSet.
			st = con.createStatement();
			rs = st.executeQuery( "SELECT * FROM pet" );

			// Print out the resulting rows.
			while( rs.next() ) {
				System.out.println( rs.getString( "name" ) + ", " + rs.getString( "owner" ) + ", " +
						rs.getString( "species" ) + ", " + rs.getString( "sec" ) + ", " +
						rs.getString( "birth" ) + ", " + rs.getString( "death" ) + "." );
			}

		} catch ( Exception e ) {
			System.err.println( "Exception: " + e.getMessage() );
			
		} finally {
			try {
				if ( rs != null ) {
					rs.close();
				}
				if ( st != null ) {
					st.close();
				}
				if ( con != null ) {
					con.close();
				}
			} catch ( SQLException e ) {
				System.err.println( "SQLException: " + e.getMessage() );
			}
		}
	}
}
