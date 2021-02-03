package nl.cookplanner.execptions;

public class ErrorResponse {

	private String message;
	private int code;
	private String moreInfo;
	
	public ErrorResponse(String message, int code) {
		this(message, code, "Geen extra informatie beschikbaar");
    }
	
	public ErrorResponse(String message, int code, String moreInfo) {
		this.message = message;
        this.code = code;
		this.moreInfo = moreInfo;
	}

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
    
    public String getMoreInfo() {
    	return moreInfo;
    }
}
