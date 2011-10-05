package domain.objects;

/**
 * @author acb1485
 * @version 1.0
 */
public enum TransactionType {	
	CHECK("Check"), DEPOSIT("Deposit"), TRANSFER("Transfer"), INTEREST("Interest Payment"), FEE("Fee");
	
	private String name;
	
	private TransactionType( String name ) {
		this.name = name;
	}
	
	public String getTypeName() {
		return name;
	}
	
}
