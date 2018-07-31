package training.api;

public class Common {
	public static boolean isStringSafe(String string) {
		return !string.contains("\"") && !string.contains("\'") && !string.contains(";") && !string.contains("\\");
	}

	public static String stringFailureMessage(String string){
		return string+" cannot be null or contain ',\",\\,;";
	}

	public static String idFailureMessage(String string){
		return string+" cannot be null or less than 0.";
	}
}
