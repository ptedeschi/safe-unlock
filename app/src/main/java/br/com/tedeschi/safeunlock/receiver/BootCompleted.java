package br.com.tedeschi.safeunlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import br.com.tedeschi.safeunlock.service.UnlockService;

public class BootCompleted extends BroadcastReceiver {
    public BootCompleted() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive BootCompleted");

        if (null != context && null != intent) {
            String action = intent.getAction();

            if (!TextUtils.isEmpty(action)) {
                if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                    Toast.makeText(context, "ACTION_BOOT_COMPLETED", Toast.LENGTH_SHORT).show();

                    // Start Service On Boot Start Up
                    Intent service = new Intent(context, UnlockService.class);
                    context.startService(service);
                }
            }
        }
    }
}
