package br.com.tedeschi.safeunlock.business;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import br.com.tedeschi.safeunlock.manager.DeviceManager;
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

                    if (null != ssid && connectionBO.isSafe(ssid)) {
                        Log.d(TAG, "Inside safe area. Disabling the keyguard...");

                        //if (Util.isScreenOn(context) && !KeyguardLockManager.getInstance().isKeyguardShowing()) {
                            Log.d(TAG, "WPT014 UNLOCK NOW");

                            DeviceManager.getInstance().setPassword(context, "");
                            KeyguardLockManager.getInstance().unlock();
                        //}
                    } else {
                        Log.d(TAG, "Outside safe area. Reenabling the keyguard...");

                        DeviceManager.getInstance().setPassword(context, "1111");
                        KeyguardLockManager.getInstance().lock();
                    }
                } else {
                    // invalid wifiInfo
                }
            } else {
                // invalid wifiManager
            }
        } else {
            // invalid context
        }

        Log.d(TAG, "-handleChange");
    }
}
