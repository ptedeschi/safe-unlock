package br.com.tedeschi.safeunlock.business;

import com.flurry.android.FlurryAgent;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.tedeschi.safeunlock.R;

/**
 * Created by tedeschi on 8/24/14.
 */
public class Analytics {
    private static final String TAG = Analytics.class.getSimpleName();

    public static void onStartApp(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onStartSession(context, context.getString(R.string.flurry_api_key));
            FlurryAgent.setCaptureUncaughtExceptions(true);
            FlurryAgent.setLogEnabled(true);

            Log.d(TAG, "onStartApp");
        }
    }

    public static void onStopApp(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onEndSession(context);

            Log.d(TAG, "onStopApp");
        }
    }

    public static void onRefresh(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.logEvent("Refresh_Clicked");

            Log.d(TAG, "onRefresh");
        }
    }

    public static void onAbout(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.logEvent("About_Clicked");

            Log.d(TAG, "onAbout");
        }
    }

    public static void onShare(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.logEvent("Share_Clicked");

            Log.d(TAG, "onShare");
        }
    }

    public static void onRate(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.logEvent("Rate_Clicked");

            Log.d(TAG, "onRate");
        }
    }

    public static void onSafeAreaChanged(Context context, String name, boolean checked) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            Map<String, String> params = new HashMap<String, String>();

            params.put("Name", name);
            params.put("Checked", Boolean.toString(checked));

            FlurryAgent.logEvent("SafeArea_Changed", params, true);

            Log.d(TAG, "onSafeAreaChanged");
        }
    }

    public static void onAppEnableChanged(Context context, boolean enabled) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            Map<String, String> params = new HashMap<String, String>();

            params.put("Enabled", Boolean.toString(enabled));

            FlurryAgent.logEvent("AppEnable_Changed", params, true);

            Log.d(TAG, "onAppEnableChanged");
        }
    }
}
