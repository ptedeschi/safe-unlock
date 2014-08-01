package br.com.tedeschi.safeunlock;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;

/**
 * Created by tedeschi on 7/19/14.
 */
public class Util {
    public static String getVersion(Context context) {
        String version = "Unknown";

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = String.format("%s.%s", packageInfo.versionName, packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public static void share(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getStoreURL(context) + "\nCheck out SafeUnlock app for Android");
        context.startActivity(Intent.createChooser(intent, "Share with"));
    }

    public static void rate(Context context) {
        Uri uri = getGooglePlayLink(context);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, getStoreURL(context)));
        }
    }

    public static boolean isScreenOn(Context context)
    {
        boolean result = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).isScreenOn();

        return result;
    }

    private static String getPackageName(Context context) {
        return context.getPackageName();
    }

    private static Uri getStoreURL(Context context) {
        return Uri.parse("http://play.google.com/store/apps/details?id=" + Util.getPackageName(context));
    }

    private static Uri getGooglePlayLink(Context context) {
        return Uri.parse("market://details?id=" + Util.getPackageName(context));
    }
}
