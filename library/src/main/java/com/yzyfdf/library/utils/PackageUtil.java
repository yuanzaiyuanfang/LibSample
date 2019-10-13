package com.yzyfdf.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

/**
 * @author sjj , 2019/5/22 17:25
 * //todo
 */
public class PackageUtil {
    private static volatile PackageUtil sInstance;

    private PackageUtil() {
    }

    public static PackageUtil getInstance() {

        if (sInstance == null) {
            synchronized (PackageUtil.class) {
                if (sInstance == null) {
                    sInstance = new PackageUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取APK的包名
     *
     * @param apkPath
     * @return
     */
    public String getPackageName(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        String packageName = null;
        if (pi != null) {
            packageName = pi.packageName;
        }
        return packageName;
    }

    /**
     * 获取APK版本名称(versionName)
     *
     * @param apkPath
     * @return
     */
    public String getVersionName(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        String versionName = null;
        if (pi != null) {
            versionName = pi.versionName;
        }
        return versionName;
    }

    /**
     * 获取APK版本号(versionCode)
     *
     * @param apkPath
     * @return
     */
    public int getVersionCode(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        int versionCode = 1;
        if (pi != null) {
            versionCode = pi.versionCode;
        }
        return versionCode;
    }

    /**
     * 获取APK的所有activity的name
     *
     * @param apkPath
     * @return
     */
    public ArrayList<String> getActivitiesName(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        ArrayList<String> list = null;
        if (pi != null) {
            list = new ArrayList<String>();
            ActivityInfo[] ais = pi.activities;
            if (ais != null) {
                for (ActivityInfo ai : ais) {
                    String name = ai.name;
                    if (name != null && !"".equals(name)) {
                        list.add(name);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取应用程序图片Drawable
     *
     * @param apkPath
     * @return
     */
    public Drawable getApkIcon(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (pi != null) {
            ApplicationInfo appInfo = pi.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(Context.getPackageManager());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * APK名称
     */
    public String getApkName(Context Context, String apkPath) {
        PackageInfo pi = Context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (pi != null) {
            ApplicationInfo appInfo = pi.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            return appInfo.loadLabel(Context.getPackageManager()).toString();
        }
        return null;
    }


    /**
     * 安装
     */
    public void installApk(Context context, String authorities, File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authorities, apk);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apk);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
