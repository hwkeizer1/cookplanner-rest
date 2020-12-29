package nl.cookplanner.domain;

/** 
 * Entries in this enumeration must not be changed 
 * Entries can be reordered
 * Entries can be added
 * Entries can contain max 20 characters
 */
public enum ShopType {

	OVERIG("Overig"),
	EKO("Eko plaza"), 
	DEKA("Deka markt"),
	MARKT("Markt");
	
	private String displayName;
	
	private ShopType(String displayName) {this.displayName = displayName;}
	
	public String getDisplayName() {return displayName;}
}