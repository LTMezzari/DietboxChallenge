package mezzari.torres.lucas.dietbox_challenge.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class ConnectionUtils {
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return connectivityManager.getActiveNetwork() != null;
        }
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
