package domain.objects;

import java.util.Vector;

/**
 * @author acb1485
 * @version 1.0
 */
public class CheckingAccount extends Account {
	
	private final static Vector<String> supportedTypes;
	
	static {
		supportedTypes = new Vector<String>();
		supportedTypes.add( TransactionType.CHECK.getTypeName() );
		supportedTypes.add( TransactionType.DEPOSIT.getTypeName() );
		supportedTypes.add( TransactionType.TRANSFER.getTypeName() );
		supportedTypes.add( TransactionType.INTEREST.getTypeName() );
		supportedTypes.add( FeeType.PRINTING.getTypeName() );
		supportedTypes.add( FeeType.OVERDRAFT.getTypeName() );
		supportedTypes.add( FeeType.WIRE.getTypeName() );
	}

	public CheckingAccount( Long accountNumber, Double accountBalance ) {
		super( accountNumber, accountBalance );
	}
	
	public Vector<String> getSupportedTransactions() {
		return supportedTypes;
	}
	
}
