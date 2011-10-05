package domain.objects;

/**
 * @author acb1485
 * @version 1.0
 */
public class AccountOwner {
	
	private String firstName;
	private String lastName;
	private String ssn;
	private String addressID;
	private String primaryPhone;
	
	public AccountOwner( String firstName, String lastName, String ssn, String addressID, String primaryPhone ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.addressID = addressID;
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

	public String getAddressID() {
		return addressID;
	}

	public void setAddressID( String addressID ) {
		this.addressID = addressID;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone( String primaryPhone ) {
		this.primaryPhone = primaryPhone;
	}
}
