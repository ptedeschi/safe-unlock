package br.com.tedeschi.safeunlock.manager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import br.com.tedeschi.safeunlock.receiver.DeviceAdmin;

/**
 * Created by patrick.tedeschi on 28/07/2014.
 */
public class AdminManager {
    private static AdminManager mInstance = null;

    private AdminManager() {

    }

    public static AdminManager getInstance() {
        if (null == mInstance) {
            mInstance = new AdminManager();
        }

        return mInstance;
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

    public void disableAdmin(Context context) {
        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.removeActiveAdmin(componentName);
    }

    public boolean isAdminEnabled(Context context) {
        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        return devicePolicyManager.isAdminActive(componentName);
    }

    public boolean setPassword(Context context, String password) {
        boolean result = false;

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        result = devicePolicyManager.resetPassword(password, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);

        return result;
    }

    public boolean resetPassword(Context context) {
        boolean result = false;

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, DeviceAdmin.class);

        devicePolicyManager.setPasswordMinimumLength(componentName, 0);
        result = devicePolicyManager.resetPassword("", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);

        return result;
    }
}
