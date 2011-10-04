package domain.objects;

/**
 * @author acb1485
 * @version 1.0
 */
public class AccountOwner {
	
	private String firstName;
	private String lastName;
	private String ssn;
	private String billingID;
	private String primaryPhone;
	
	public AccountOwner( String firstName, String lastName, String ssn, String billingID, String primaryPhone ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.billingID = billingID;
		this.primaryPhone = primaryPhone;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getBillingID() {
		return billingID;
	}

	public void setBillingID( String billingID ) {
		this.billingID = billingID;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone( String primaryPhone ) {
		this.primaryPhone = primaryPhone;
	}
}
