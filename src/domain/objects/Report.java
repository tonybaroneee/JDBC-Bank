package domain.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author acb1485
 * @version 1.0
 */
public class Report {
	
	private static final NumberFormat usFormat = NumberFormat.getCurrencyInstance( Locale.US );
	
	public Report() {
		
	}
	
	public void formatAccountInfo( ResultSet rs ) {
		try {
			System.out.print( "Account number: " + rs.getInt( "AccountNumber" ) + 
					" | Balance: " + usFormat.format( rs.getDouble( "Balance" ) ) + 
					" | Owners: " );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public void formatAccountOwners( ResultSet rs ) {
		try {
			boolean firstName = true;
			if ( rs.next() ) {
				do {
					if ( !firstName ) {
						System.out.print( ", " );
					} else {
						firstName = false;
					}
					System.out.print( rs.getString( 5 ) + " " + rs.getString( 6 ) );
				} while ( rs.next() );
			}
			System.out.println();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public void formatAccountBalances( ResultSet rs ) {
		try {
			while ( rs.next() ) {
				System.out.println( "Account number: " + rs.getInt( 2 ) + 
						" | Balance: " + usFormat.format( rs.getBigDecimal( 3 ) ) );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public void formatSpecificTransWithin30Days( ResultSet rs ) {
		try {
			while ( rs.next() ) {
				System.out.println( "Transaction ID: " + rs.getInt( 1 ) + 
						" | Account ID: " + rs.getInt( 2 ) + " | Amount: " + usFormat.format( rs.getBigDecimal( 3 ) ) +
						" | Type Name: " + rs.getString( "TypeName" ) );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public void formatAllTransactionsWithin30Days( ResultSet rs ) {
		try {
			while ( rs.next() ) {
				System.out.print( "Transaction ID: " + rs.getInt( 1 ) + 
						" | Account ID: " + rs.getInt( 2 ) + " | Amount: " + usFormat.format( rs.getBigDecimal( 3 ) ) +
						" | Type Name: " + rs.getString( "TransactionType.TypeName" ) );
				if ( rs.getString( "TransactionType.TypeName" ).equals( TransactionType.TRANSFER.getTypeName() ) ) {
					System.out.print( " | From ID: " + rs.getString( "Transaction.DestinationID" ) );
					System.out.print( " | To ID: " + rs.getString( "Transaction.SourceID" ) );
				}
				if ( rs.getString( "FeeType.TypeName" ) != null ) {
					System.out.print( " | Fee Type: " + rs.getString( "FeeType.TypeName" ) );
				}
				System.out.println();
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
	}
}
