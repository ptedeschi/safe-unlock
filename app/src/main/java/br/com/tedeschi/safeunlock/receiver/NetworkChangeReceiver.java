package br.com.tedeschi.safeunlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import br.com.tedeschi.safeunlock.KeyguardManager;
import br.com.tedeschi.safeunlock.NetworkUtil;
import br.com.tedeschi.safeunlock.business.ConnectionBO;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        System.out.println("status: " + status);

        String action = intent.getAction();

        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();

        handleChange(context);
    }

    private void handleChange(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();

        String ssid = info.getSSID();
        ConnectionBO connectionBO = new ConnectionBO(context);

        if (null != ssid && connectionBO.isSafe(ssid)) {
            Log.d(TAG, "Inside safe area. Disabling keyguard");

            KeyguardManager.getInstance().disable(context);
        } else {
            Log.d(TAG, "Outside safe area. Reenabling keyguard");

            KeyguardManager.getInstance().enable(context);
        }
    }




}
