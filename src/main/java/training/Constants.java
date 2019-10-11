package training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Constants {
    public static final String NAMESPACE_URI = "my-namespace";
    public static final String STRING_ERROR_MSG = "cannot be empty or null.";
    public static final String ID_ERROR_MSG = "cannot be null or less than 0.";
    public static final String DNE = "does not exist.";
    public static final String DNECU = "does not exist, cannot update.";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final ArrayList<String> RATINGS = new ArrayList<String>(){
        {
            add("R");
            add("G");
            add("PG");
            add("PG-13");
            add("NC-17");
        }
    };
    private Constants(){}

    public static String formatDate(Date date){
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public static Date formatString(String s){
        try {
            return (Date) new SimpleDateFormat(DATE_FORMAT).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
