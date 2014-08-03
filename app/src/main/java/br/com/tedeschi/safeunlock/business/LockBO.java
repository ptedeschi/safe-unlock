package br.com.tedeschi.safeunlock.business;

import android.content.Context;
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
                    SettingsBO settingsBO = new SettingsBO(context);

                    if (!TextUtils.isEmpty(ssid) && connectionBO.isSafe(ssid)) {
                        if (settingsBO.isEnabled()) {
                            Log.d(TAG, "Inside safe area. Ordering to disable the keyguard");

                            KeyguardLockManager.getInstance().unlock();
                        } else {
                            Log.d(TAG, "App is disabled. Ordering to enable the keyguard...");

                            KeyguardLockManager.getInstance().lock();
                        }
                    } else {
                        Log.d(TAG, "Outside safe area. Ordering to enable the keyguard...");

                        KeyguardLockManager.getInstance().lock();
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
}
