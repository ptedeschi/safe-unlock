package br.com.tedeschi.safeunlock.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import br.com.tedeschi.safeunlock.DeviceAdmin;
import br.com.tedeschi.safeunlock.business.ConnectionBO;

// http://khurramitdeveloper.blogspot.in/2013/06/start-activity-or-service-on-boot.html
public class UnlockService extends Service {

    private KeyguardManager.KeyguardLock mLock;
    private BroadcastReceiver mNetworkChangeReceiver;

    public UnlockService() {
    }

    private void disablelockDevice() {
        this.mLock.disableKeyguard();
        resetPassword(this);
    }

    private void enablelockDevice() {
        this.mLock.reenableKeyguard();
        setPassword(this, "1111");
    }

    public void enableAdmin(Context context) {
        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Your boss told you to do this");
        context.startActivity(intent);
    }

    public void disableAdmin() {

    }

    public boolean isAdminEnabled() {
        return false;
    }

    public void setPassword(Context context, String password) {
        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        boolean result = devicePolicyManager.resetPassword(password, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
    }

    public void resetPassword(Context context) {
        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        boolean result = devicePolicyManager.resetPassword("", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_SHORT).show();
        super.onCreate();

        //enableAdmin(this);

        mNetworkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        //filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(mNetworkChangeReceiver, filter);

        this.mLock = ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("br.com.tedeschi.safeunlock.keyguard");
    }


    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Working", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        private final String TAG = NetworkChangeReceiver.class.getSimpleName();

        public NetworkChangeReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("WPT014 " + intent.getAction());


            handleChange(context);
        }

        private void handleChange(Context context) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();

            String ssid = info.getSSID();
            ConnectionBO connectionBO = new ConnectionBO(context);

            if (null != ssid && connectionBO.isSafe(ssid)) {
                Log.d(TAG, "Inside safe area. Disabling keyguard");

                disablelockDevice();

                Toast.makeText(context, "Keyguard disabled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Outside safe area. Reenabling keyguard");

                enablelockDevice();

                Toast.makeText(context, "Keyguard reenabled", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
