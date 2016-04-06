# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#极光推送混淆部分
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
#极光推送混淆部分结束
#glide 混淆配置
-keepnames class com.mypackage.MyGlideModule
# or more generally:
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
  }
  #glide 结束
  #友盟统计混淆部分
  -keepclassmembers class * {
     public <init> (org.json.JSONObject);
  }
  -keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
  }

#友盟统计混淆配置部分结束
#xutils 配置
 -libraryjars libs/xUtils-2.6.14.jar
 -keep class com.lidroid.** { *; }
 #xutils 配置结束
 #微信登录
-keep class com.tencent.mm.sdk.** {
   *;
}
 #微信登录结束