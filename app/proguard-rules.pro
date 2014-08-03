# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/Android Studio.app/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

# Avoid " can't find referenced class com.actionbarsherlock.BuildConfig" warnings
-dontwarn com.actionbarsherlock.internal.**

# Keep classes from third-party libraries
-keep class android.support.** {* ; }
-keep class com.google.android.gms.** {* ; }
-keep class com.actionbarsherlock.** {* ; }
-keep class de.greenrobot.** {* ; }
-keep class org.jraf.** {* ; }
-keep class com.flurry.android.** {* ; }

# Keep classes that can't have their fields changed
-keep class br.com.tedeschi.safeunlock.persistence.** {* ; }

# Remove all logging API calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
	public static int wtf(...);
}
