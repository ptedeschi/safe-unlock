package br.com.tedeschi.safeunlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        System.out.println("WPT014 status: " + status);

        String action = intent.getAction();

        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();

        handleChange(context);
    }

    private void handleChange(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();

        String ssid = info.getBSSID ();

        if (null != ssid && ssid.equals("b0:c5:54:b3:0b:54")) {
            Log.d(TAG, "WPT014 Inside safe area. Disabling keyguard");

            KeyguardManager.getInstance().disable(context);
            //DeviceAdminUtil.getInstance().resetPassword(context);
        } else {
            Log.d(TAG, "WPT014 Outside safe area. Reenabling keyguard");

            KeyguardManager.getInstance().enable(context);
            //DeviceAdminUtil.getInstance().setPassword(context, "3105");
        }
    }




}
