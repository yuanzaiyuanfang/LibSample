package com.yzyfdf.library.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

/**
 * @author sjj , 2019/5/22 17:25
 * //todo
 */
class PackageUtil private constructor() {

    /**
     * 获取APK的包名
     *
     * @param apkPath
     * @return
     */
    fun getPackageName(Context: Context, apkPath: String): String? {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        var packageName: String? = null
        if (pi != null) {
            packageName = pi.packageName
        }
        return packageName
    }

    /**
     * 获取APK版本名称(versionName)
     *
     * @param apkPath
     * @return
     */
    fun getVersionName(Context: Context, apkPath: String): String? {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        var versionName: String? = null
        if (pi != null) {
            versionName = pi.versionName
        }
        return versionName
    }

    /**
     * 获取APK版本号(versionCode)
     *
     * @param apkPath
     * @return
     */
    fun getVersionCode(Context: Context, apkPath: String): Int {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        var versionCode = 1
        if (pi != null) {
            versionCode = pi.versionCode
        }
        return versionCode
    }

    /**
     * 获取APK的所有activity的name
     *
     * @param apkPath
     * @return
     */
    fun getActivitiesName(Context: Context, apkPath: String): ArrayList<String>? {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        var list: ArrayList<String>? = null
        if (pi != null) {
            list = ArrayList()
            val ais = pi.activities
            if (ais != null) {
                for (ai in ais) {
                    val name = ai.name
                    if (name != null && "" != name) {
                        list.add(name)
                    }
                }
            }
        }
        return list
    }

    /**
     * 获取应用程序图片Drawable
     *
     * @param apkPath
     * @return
     */
    fun getApkIcon(Context: Context, apkPath: String): Drawable? {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        if (pi != null) {
            val appInfo = pi.applicationInfo
            appInfo.sourceDir = apkPath
            appInfo.publicSourceDir = apkPath
            try {
                return appInfo.loadIcon(Context.packageManager)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
            }

        }
        return null
    }

    /**
     * APK名称
     */
    fun getApkName(Context: Context, apkPath: String): String? {
        val pi = Context.packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES)
        if (pi != null) {
            val appInfo = pi.applicationInfo
            appInfo.sourceDir = apkPath
            appInfo.publicSourceDir = apkPath
            return appInfo.loadLabel(Context.packageManager)
                .toString()
        }
        return null
    }


    /**
     * 安装
     */
    fun installApk(context: Context, authorities: String, apk: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authorities, apk)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            uri = Uri.fromFile(apk)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    companion object {

        val instance: PackageUtil by lazy {
            PackageUtil()
        }

    }

}
