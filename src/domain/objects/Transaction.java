package domain.objects;

import java.util.Date;

/**
 * @author acb1485
 * @version 1.0
*/
public class Transaction {
	
	private Date timestamp;
	private Double amount;
	private TransactionType type;
	
	public Transaction ( Date timestamp, Double amount, TransactionType type ) {
		this.timestamp = timestamp;
		this.amount = amount;
		this.type = type;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp( Date timestamp ) {
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount( Double amount ) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType( TransactionType type ) {
		this.type = type;
	}
}
