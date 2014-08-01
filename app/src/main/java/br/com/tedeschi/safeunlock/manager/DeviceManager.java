package br.com.tedeschi.safeunlock.manager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.tedeschi.safeunlock.receiver.DeviceAdmin;

/**
 * Created by patrick.tedeschi on 28/07/2014.
 */
public class DeviceManager {
    private static final String TAG = DeviceManager.class.getSimpleName();
    private static DeviceManager mInstance = null;

    private DeviceManager() {
        Log.d(TAG, "+DeviceManager");

        Log.d(TAG, "-DeviceManager");
    }

    public static DeviceManager getInstance() {
        if (null == mInstance) {
            mInstance = new DeviceManager();
        }

        return mInstance;
    }

    public void enableAdmin(Context context) {
        Log.d(TAG, "+enableAdmin");

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Your boss told you to do this");
        context.startActivity(intent);

        Log.d(TAG, "-enableAdmin");
    }

    public void disableAdmin(Context context) {
        Log.d(TAG, "+disableAdmin");

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.removeActiveAdmin(componentName);

        Log.d(TAG, "-disableAdmin");
    }

    public boolean isAdminEnabled(Context context) {
        Log.d(TAG, "+isAdminEnabled");

        boolean result = false;

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        result = devicePolicyManager.isAdminActive(componentName);

        Log.d(TAG, "-isAdminEnabled: " + result);

        return result;
    }

    public boolean setPassword(Context context, String password) {
        Log.d(TAG, "+setPassword");

        boolean result = false;

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        result = devicePolicyManager.resetPassword(password, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);

        Log.d(TAG, "-setPassword: " + result);

        return result;
    }

    public boolean resetPassword(Context context) {
        Log.d(TAG, "+resetPassword");

        boolean result = false;

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        result = devicePolicyManager.resetPassword("", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);

        Log.d(TAG, "-resetPassword: " + result);

        return result;
    }
}
