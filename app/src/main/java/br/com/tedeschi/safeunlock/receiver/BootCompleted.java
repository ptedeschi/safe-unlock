package br.com.tedeschi.safeunlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import br.com.tedeschi.safeunlock.service.UnlockService;

public class BootCompleted extends BroadcastReceiver {

    private static final String TAG = BootCompleted.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "+onReceive");

        if (null != context) {
            if (null != intent) {
                String action = intent.getAction();

                if (!TextUtils.isEmpty(action)) {
                    Log.d(TAG, "Action: " + action);

                    if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                        // Starts service after device is started

                        Log.d(TAG, "Boot completed... Starting Unlock Service");

                        Intent service = new Intent(context, UnlockService.class);
                        context.startService(service);
                    }
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
