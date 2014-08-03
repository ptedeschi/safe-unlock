package br.com.tedeschi.safeunlock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.flurry.android.FlurryAgent;

import br.com.tedeschi.safeunlock.Constants;
import br.com.tedeschi.safeunlock.R;
import br.com.tedeschi.safeunlock.business.LockBO;
import br.com.tedeschi.safeunlock.manager.KeyguardLockManager;

// http://khurramitdeveloper.blogspot.in/2013/06/start-activity-or-service-on-boot.html
public class UnlockService extends Service {


    private static final String TAG = UnlockService.class.getSimpleName();
    private BroadcastReceiver mNetworkChangeReceiver;

    public UnlockService() {
        Log.d(TAG, "+UnlockService");

        Log.d(TAG, "-UnlockService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "+onBind");

        Log.d(TAG, "-onBind");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "+onCreate");

        Log.d(TAG, "Service Created");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onStartSession(this, getString(R.string.flurry_api_key));
            FlurryAgent.setCaptureUncaughtExceptions(true);
            FlurryAgent.setLogEnabled(true);
        }

        KeyguardLockManager.getInstance().initialize(this);

        registerReceivers();

        Log.d(TAG, "-onCreate");
    }

    private void registerReceivers() {
        Log.d(TAG, "+registerReceivers");

        mNetworkChangeReceiver = new StatusChangeReceiver();
        IntentFilter filter = new IntentFilter();

        // Monitor changes in Network state
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        // Monitor changes in Screen state
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        // Monitor when user is present
        filter.addAction(Intent.ACTION_USER_PRESENT);

        // Monitor changes in Safe list
        filter.addAction(Constants.ACTION_SAFE_CHANGED);

        registerReceiver(mNetworkChangeReceiver, filter);

        Log.d(TAG, "-registerReceivers");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service Destroy");

        unregisterReceiver(mNetworkChangeReceiver);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onEndSession(this);
        }

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service Working");

        return super.onStartCommand(intent, flags, startId);
    }

    private class StatusChangeReceiver extends BroadcastReceiver {

        private final String TAG = StatusChangeReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "+onReceive");

            if (null != context) {
                if (null != intent) {
                    String action = intent.getAction();

                    if (!TextUtils.isEmpty(action)) {
                        Log.d(TAG, "Action: " + action);

                        LockBO.handleChange(context);
                    } else {
                        Log.d(TAG, "Invalid action");
                    }
                } else {
                    Log.d(TAG, "Invalid Intent");
                }
            } else {
                Log.d(TAG, "Invalid context");
            }

            Log.d(TAG, "+onReceive");
        }
    }
}
