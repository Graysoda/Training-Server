package training.api.rest;

public class RestConstants {
    public static final String JSON = "application/json";
    public static final String REST_SERVICES_LOCATION = "/rest";
    private RestConstants(){}

	static boolean isStringSafe(String string) {
		return !string.contains("\"") && !string.contains("\'") && !string.contains(";") && !string.contains("\\");
	}

	static String stringFailureMessage(String string){
    	return string+" cannot be null or contain ',\",\\,;";
	}

	static String idFailureMessage(String string){
    	return string+" cannot be null or less than 0.";
	}
}
