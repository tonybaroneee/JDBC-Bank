package domain.objects;

import java.util.Date;

/**
 * @author acb1485
 * @version 1.0
 */
public class Fee extends Transaction {
	
	private TransactionType feeType;
	
	public Fee( Date timestamp, Double amount, TransactionType type, TransactionType feeType ) {
		super( timestamp, amount, type );
		this.feeType = feeType;
	}

	public TransactionType getFeeType() {
		return feeType;
	}

	public void setFeeType( TransactionType feeType ) {
		this.feeType = feeType;
	}
}
