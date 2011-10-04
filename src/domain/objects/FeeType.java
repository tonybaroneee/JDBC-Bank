package domain.objects;

/**
 * @author acb1485
 * @version 1.0
 */
public enum FeeType {	
	PRINTING("Check Printing"), OVERDRAFT("Overdraft"), WIRE("Wire Transfer");
	
	private String name;
	
	private FeeType( String name ) {
		this.name = name;
	}
	
	public String getTypeName() {
		return name;
	}
	
}
