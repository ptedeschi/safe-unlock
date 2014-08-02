package br.com.tedeschi.safeunlock.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import br.com.tedeschi.safeunlock.manager.KeyguardLockManager;

/**
 * Created by tedeschi on 7/31/14.
 */
public class LockBO {
    private static final String TAG = LockBO.class.getSimpleName();
    public static final String PREFS_NAME = "PrefsFile";
    public static final String PREFS_LOCKED_KEY = "locked";

    public static void handleChange(Context context) {
        Log.d(TAG, "+handleChange");

        if (null != context) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            if (null != wifiManager) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                if (null != wifiInfo) {
                    String ssid = wifiInfo.getSSID();

                    Log.d(TAG, "SSID: " + ssid);

                    ConnectionBO connectionBO = new ConnectionBO(context);

                    if (!TextUtils.isEmpty(ssid) && connectionBO.isSafe(ssid)) {
                        Log.d(TAG, "Inside safe area. Disabling the keyguard");

                        if (!KeyguardLockManager.getInstance().isKeyguardShowing()) {
                            KeyguardLockManager.getInstance().unlock();
                        }
                    } else {
                        Log.d(TAG, "Outside safe area. Reenabling the keyguard...");

                        if (!KeyguardLockManager.getInstance().isKeyguardShowing()) {
                            KeyguardLockManager.getInstance().lock();
                        }
                    }
                } else {
                    Log.e(TAG, "Invalid wifiInfo");
                }
            } else {
                Log.e(TAG, "Invalid wifiManager");
            }
        } else {
            Log.e(TAG, "Invalid context");
        }

        Log.d(TAG, "-handleChange");
    }

    private static boolean isLocked(Context context) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(PREFS_LOCKED_KEY, false);
    }

    private static void setIsLocked(Context context, boolean locked) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFS_LOCKED_KEY, locked);
        editor.commit();
    }
}
