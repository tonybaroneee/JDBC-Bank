package domain.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;

import domain.objects.TransactionType;

/**
 * @author acb1485
 * @version 1.0
 */
public class BankMain {
	
	private static final NumberFormat usFormat = NumberFormat.getCurrencyInstance( Locale.US );
	
	private final static String COLNAME_ACCTNUM = "AccountNumber";
	private final static String COLNAME_ACCTBAL = "AccountBalance";

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
		
		
		String fetchAllAccountsQuery = "SELECT * FROM Account";
		String fetchAllOwnersForAccount = "SELECT * FROM AccountOwnerAccount, AccountOwner " +
				"WHERE AccountOwnerAccount.AccountID = ? " +
				"AND AccountOwnerAccount.AccountOwnerID = AccountOwner.AccountOwnerID";
		
		String fetchAllAccountsAbove5kQuery = "SELECT * FROM Account WHERE Balance>5000.00";
		
		String fetchTransactionTypeID = "SELECT TypeID FROM TransactionType WHERE TypeName=?";
		String fetchAllChecksWithin30DaysFromAccount = "SELECT * FROM Transaction " +
				"WHERE AccountID = ? " +
				"AND TypeID = ? " +
				"AND DATEDIFF(CURDATE(),DATE(Datestamp))<31";

		try {
			// Make the initial connection with our database.
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			con = DriverManager.getConnection( url, user, password );
			System.out.println( "Connection was successfully established." );
			
			/////////// QUERY #1 /////////////

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
			
			/////////// QUERY #2 /////////////
			
			// Construct our query then execute it, storing the ResultSet.
			st = con.createStatement();
			rs = st.executeQuery( fetchAllAccountsAbove5kQuery );

			// Print out the resulting rows.
			while ( rs.next() ) {
				System.out.println( "Account number: " + rs.getInt( 2 ) + 
						" | Balance: " + usFormat.format( rs.getBigDecimal( 3 ) ) );
			}
			
			/////////// QUERY #3 /////////////

			// Construct our query then execute it, storing the ResultSet.
			rs = null;
			pst = null;
			int checkID = -1;
			pst = con.prepareStatement( fetchTransactionTypeID );
			pst.setString( 1, TransactionType.CHECK.getTypeName() );
			rs = pst.executeQuery();
			if ( rs2.next() ) {
				checkID = rs.getInt( 1 );
			}

			rs = null;
			pst = con.prepareStatement( fetchAllChecksWithin30DaysFromAccount );
			pst.setInt( 1, 1 ); // TODO: HARD-CODED ACCOUNT ID!!
			pst.setInt( 2, 1 );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				System.out.println( "Transaction ID: " + rs.getInt( 1 ) + 
						" | Account ID: " + rs.getInt( 2 ) + " | Amount: " + usFormat.format( rs.getBigDecimal( 3 ) ) );
			}
			

		} catch ( Exception e ) {
			System.err.println( "Exception: " + e.getMessage() );
			
		} finally {
			try {
				if ( rs  != null ) 	{ rs.close();  }
				if ( rs2 != null ) 	{ rs2.close(); }
				if ( st  != null ) 	{ st.close();  }
				if ( pst != null ) 	{ pst.close(); }
				if ( con != null ) 	{ con.close(); }
			} catch ( SQLException e ) {
				System.err.println( "SQLException: " + e.getMessage() );
			}
		}
	}
}
