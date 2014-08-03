package br.com.tedeschi.safeunlock.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.tedeschi.safeunlock.persistence.vo.Connection;

/**
 * Created by tedeschi on 7/14/14.
 */
public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    public static int TYPE_WIFI = 1;

    public static int TYPE_MOBILE = 2;

    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }

        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkManager.getConnectivityStatus(context);
        String status = null;

        if (conn == NetworkManager.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkManager.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkManager.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }

        return status;
    }

    public static List<Connection> getConfiguredNetworks(Context context) {
        Log.d(TAG, "+getConfiguredNetworks");

        List<Connection> list = null;

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (null != wifiManager) {
            List<WifiConfiguration> wifiConfigurationList = wifiManager.getConfiguredNetworks();

            if (null != wifiConfigurationList && wifiConfigurationList.size() > 0) {
                list = new ArrayList<Connection>();

                for (WifiConfiguration x : wifiConfigurationList) {
                    if (!TextUtils.isEmpty(x.SSID)) {
                        Log.d(TAG, "Found configured network: " + x);

                        Connection hotspot = new Connection();

                        String ssid = x.SSID.replace("\"", "");

                        hotspot.setName(ssid);
                        hotspot.setUniqueId(ssid);
                        hotspot.setType(0);
                        hotspot.setChecked(false);

                        list.add(hotspot);
                    }
                }
            } else {
                Log.d(TAG, "Could not find any configured networks");
            }
        } else {
            Log.e(TAG, "Invalid WifiManager");
        }

        Log.d(TAG, "-getConfiguredNetworks");

        return list;
    }
}
