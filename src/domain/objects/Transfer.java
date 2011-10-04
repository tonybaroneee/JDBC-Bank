package domain.objects;

import java.util.Date;

/**
 * @author acb1485
 * @version 1.0
 */
public class Transfer extends Transaction {
	
	private Long sourceAcct;
	private Long destAcct;

	public Transfer( Date timestamp, Double amount, TransactionType type, Long sourceAcct, Long destAcct ) {
		super( timestamp, amount, type );
		this.sourceAcct = sourceAcct;
		this.destAcct = destAcct;
	}

	public Long getSourceAcct() {
		return sourceAcct;
	}

	public void setSourceAcct( Long sourceAcct ) {
		this.sourceAcct = sourceAcct;
	}

	public Long getDestAcct() {
		return destAcct;
	}

	public void setDestAcct( Long destAcct ) {
		this.destAcct = destAcct;
	}

}
