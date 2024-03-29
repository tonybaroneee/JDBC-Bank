package domain.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author acb1485
 * @version 1.0
 */
public abstract class Account {
	
	private Long accountNumber;
	private Double accountBalance;
	private List<Transaction> accountTransactions;
	
	public Account( Long accountNumber, Double accountBalance ) {
		accountNumber = this.accountNumber;
		accountBalance = this.accountBalance;
		accountTransactions = new ArrayList<Transaction>();
	}
	
	public void addTransaction( Date timstamp, TransactionType type, Double amount ) {
		// Normal transaction request (not a transfer and not a fee).
		addTransaction( timstamp, type, amount, null, null, null );
	}
	
	public void addTransaction( Date timstamp, TransactionType type, Double amount, TransactionType feeType ) {
		// Fee transaction request.
		addTransaction( timstamp, type, amount, null, null, feeType );
	}
	
	public void addTransaction( Date timstamp, TransactionType type, Double amount, Long sourceAcct, Long destAcct ) {
		// Transfer transaction request.
		addTransaction( timstamp, type, amount, sourceAcct, destAcct, null );
	}
	
	public void addTransaction( Date timstamp, TransactionType type, Double amount, Long sourceAcct, Long destAcct, TransactionType feeType ) {
		// Determine which type of Transaction to create, then add it to our collection.
		Transaction newTransaction;
		if ( feeType != null ) {
			newTransaction = new Fee( timstamp, amount, type, feeType );
			accountTransactions.add( newTransaction );
		} else if ( sourceAcct != null && destAcct != null ) {
			newTransaction = new Transfer( timstamp, amount, type, sourceAcct, destAcct );
			accountTransactions.add( newTransaction );
		} else {
			newTransaction = new Transaction( timstamp, amount, feeType );
		}
		adjustBalance( newTransaction );
	}
	
	public void adjustBalance( Transaction t ) {
		if ( t.getType().getTypeName().equals( TransactionType.CHECK ) ||
			 t.getType().getTypeName().equals( TransactionType.FEE ) ) {
			this.accountBalance -= t.getAmount();
		} else if ( t.getType().getTypeName().equals( TransactionType.DEPOSIT ) ||
				t.getType().getTypeName().equals( TransactionType.INTEREST ) ) {
			this.accountBalance += t.getAmount();
		} else if ( t instanceof Transfer ) {
			Transfer tempTransfer = (Transfer)t;
			if ( tempTransfer.getSourceAcct().longValue() == this.accountNumber.longValue() ) {
				this.accountBalance -= tempTransfer.getAmount();
			} else {
				this.accountBalance += tempTransfer.getAmount();
			}
		}
	}
	
	public void recalculateBalance() {
		double tempBalance = 0.0;
		for ( Transaction t : accountTransactions ) {
			if ( t.getType().getTypeName().equals( TransactionType.CHECK ) ||
				 t.getType().getTypeName().equals( TransactionType.FEE ) ) {
				tempBalance -= t.getAmount();
			} else if ( t.getType().getTypeName().equals( TransactionType.DEPOSIT ) ||
					t.getType().getTypeName().equals( TransactionType.INTEREST ) ) {
				tempBalance += t.getAmount();
			} else if ( t instanceof Transfer ) {
				Transfer tempTransfer = (Transfer)t;
				if ( tempTransfer.getSourceAcct().longValue() == this.accountNumber.longValue() ) {
					tempBalance -= tempTransfer.getAmount();
				} else {
					tempBalance += tempTransfer.getAmount();
				}
			}
		}
		this.accountBalance = tempBalance;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber( Long accountNumber ) {
		this.accountNumber = accountNumber;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance( Double accountBalance ) {
		this.accountBalance = accountBalance;
	}

	public List<Transaction> getAccountTransactions() {
		return accountTransactions;
	}
	
}
