package bank.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author acb1485
 * @version 1.0
 */
public class BankMain {
	
	private static final NumberFormat usFormat = NumberFormat.getCurrencyInstance( Locale.US );  

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
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		// Specify the main queries used in our reporting mechanism.
		String fetchAllAccountsQuery = "SELECT * FROM CheckingAccount";
		String fetchAllOwnersForAccount = "SELECT * FROM AccountOwnerAccount, AccountOwner " +
				"WHERE AccountOwnerAccount.CheckingAccountID = ? " +
				"AND AccountOwnerAccount.AccountOwnerID = AccountOwner.AccountOwnerID";

		try {
			// Make the initial connection with our database.
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			con = DriverManager.getConnection( url, user, password );
			System.out.println( "Connection was successfully established." );

			// Construct our query then execute it, storing the ResultSet.
			st = con.createStatement();
			rs = st.executeQuery( fetchAllAccountsQuery );

			// Print out the resulting rows.
			while ( rs.next() ) {
				System.out.print( "Account number: " + rs.getInt( 2 ) + 
						" | Balance: " + usFormat.format( rs.getBigDecimal( 3 ) ) + 
						" | Owners: " );
				
				// Find out who owns this particular checking account.
				rs2 = null;
				pst = con.prepareStatement( fetchAllOwnersForAccount );
				pst.setInt( 1, rs.getInt(1) );
				rs2 = pst.executeQuery();
				boolean firstName = true;
				if ( rs2.next() ) {
					do {
						if ( !firstName ) {
							System.out.print( ", " );
						} else {
							firstName = false;
						}
						System.out.print( rs2.getString( 5 ) + " " + rs2.getString( 6 ) );
					} while ( rs2.next() );
				}
				
				System.out.println();
				
			}

		} catch ( Exception e ) {
			System.err.println( "Exception: " + e.getMessage() );
			
		} finally {
			try {
				if ( rs != null ) {
					rs.close();
				}
				if ( rs2 != null ) {
					rs2.close();
				}
				if ( st != null ) {
					st.close();
				}
				if ( pst != null ) {
					pst.close();
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
