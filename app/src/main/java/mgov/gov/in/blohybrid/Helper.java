package mgov.gov.in.blohybrid;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abc- on 1/2/2017.
 */
public class Helper {

    private static Helper mHelper ;

    private Helper(){

    }

    public static Helper getmHelper(){
        if(mHelper==null)
            mHelper = new Helper();
        return mHelper;
    }

    public void v(String tag, String message){
        Log.v(tag, message);
    }

    public boolean isValidEmail(String enteredEmail){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(enteredEmail);
        return matcher.matches();
    }
}
