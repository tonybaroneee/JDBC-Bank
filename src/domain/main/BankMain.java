package domain.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.objects.Report;
import domain.objects.TransactionType;

/**
 * @author acb1485
 * @version 1.0
 */
public class BankMain {

	// Initialize MySQL DB information into class variables.
	private final static String url = "jdbc:mysql://mentok.se.rit.edu:3306/acb1485";
	private final static String user = "acb1485";
	private final static String password = "Eigh7fiung";
	
	// Used for Report #1
	private final static String fetchAllAccountsQuery = "SELECT * FROM Account";
	private final static String fetchAllOwnersForAccount = "SELECT * FROM AccountOwnerAccount, AccountOwner " +
			"WHERE AccountOwnerAccount.AccountID = ? " +
			"AND AccountOwnerAccount.AccountOwnerID = AccountOwner.AccountOwnerID";
	
	// Used for Report #2
	private final static String fetchAllAccountsAbove5kQuery = "SELECT * FROM Account WHERE Balance>? ORDER BY Balance DESC";
	
	// Used for Report #3
	private final static String fetchAllChecksWithin30DaysFromAccount = "SELECT * FROM Transaction " +
			"LEFT JOIN TransactionType ON Transaction.TypeID = TransactionType.TypeID " +
			"WHERE AccountID = ? " +
			"AND TypeName = ? " +
			"AND DATEDIFF(CURDATE(),DATE(Datestamp))<31";
	
	// Used for Report #4
	private final static String fetchAllTransWithin30DaysFromAccountSorted = "SELECT * FROM Transaction " +
			"LEFT JOIN FeeType ON Transaction.FeeTypeID = FeeType.TypeID " +
			"LEFT JOIN TransactionType ON Transaction.TypeID = TransactionType.TypeID " +
			"WHERE AccountID = ? " +
			"AND DATEDIFF(CURDATE(),DATE(Datestamp))<31 " +
			"ORDER BY TransactionType.TypeName, FeeType.TypeName";	

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		System.out.println( "Bank application started." );
		System.out.println( "" );

		// Initialize our JDBC variables.
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			// Make the initial connection with our database.
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			con = DriverManager.getConnection( url, user, password );
			System.out.println( "Connection was successfully established." );
			
			Report reporter = new Report();
			
			/**
			 * Report #1: A list of all accounts, displaying the account number, account owners, and account balance.
			 */

			// Construct our query then execute it, storing the ResultSet.
			st = con.createStatement();
			rs = st.executeQuery( fetchAllAccountsQuery );

			System.out.println();
			System.out.println("Report #1: A list of all accounts, displaying the account number, account owners, and account balance.");
			System.out.println("---");
			while ( rs.next() ) {
				// Print out the account information.
				reporter.formatAccountInfo( rs );
				
				// Find out who owns this particular checking account.
				rs2 = null;
				pst = con.prepareStatement( fetchAllOwnersForAccount );
				pst.setInt( 1, rs.getInt(1) );
				rs2 = pst.executeQuery();
				
				// Print out the account owners.
				reporter.formatAccountOwners( rs2 );
			}
			
			/**
			 * Report #2: A list of all account numbers and account balance where the account balance is more than $5,000.
			 */
			
			// Construct our query then execute it, storing the ResultSet.			
			pst = con.prepareStatement( fetchAllAccountsAbove5kQuery );
			pst.setDouble( 1, 5000.00 );
			rs = pst.executeQuery();

			// Print out the resulting rows.
			System.out.println();
			System.out.println("Report #2: A list of all account numbers and account balance where the account balance is more than $5,000.");
			System.out.println("---");
			reporter.formatAccountBalances( rs );
			
			/**
			 * Report #3: For a given account (I chose Account #1), a list of all checks written in the past 30 calendar days
			 */

			// Construct our query then execute it, storing the ResultSet.
			pst = con.prepareStatement( fetchAllChecksWithin30DaysFromAccount );
			pst.setInt( 1, 1 );
			pst.setString( 2, TransactionType.CHECK.getTypeName() );
			rs = pst.executeQuery();
			
			System.out.println();
			System.out.println("Report #3: For a given account (I chose Account #1), a list of all checks written in the past 30 calendar days.");
			System.out.println("---");
			reporter.formatSpecificTransWithin30Days( rs ); 
			
			/**
			 * Report #4:  For a given account (I chose Account #1), a list of all account transactions in the last 30 calendar days, 
			 * 					grouped by transaction type: deposit, check, transfer, interest payment, and fee
			 * 				*Challenge:  further subgroup by fee type: (check printing, overdraft, or wire transfer)
			 */

			// Construct our query then execute it, storing the ResultSet.
			pst = con.prepareStatement( fetchAllTransWithin30DaysFromAccountSorted );
			pst.setInt( 1, 1 );
			rs = pst.executeQuery();
			
			// Print out the resulting transactions.
			System.out.println();
			System.out.println("Report #4: For a given account (I chose Account #1), a list of all account transactions in the last 30 calendar days (sort by Type,FeeType).");
			System.out.println("---");
			reporter.formatAllTransactionsWithin30Days( rs );
			

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
