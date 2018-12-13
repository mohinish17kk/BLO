package mgov.gov.in.blohybrid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.Settings;

/**
 * Created by mohinish on 3/30/2016.
 */
public class ConnectionStatus {
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null;
    }
    public static String getDeviceID(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
