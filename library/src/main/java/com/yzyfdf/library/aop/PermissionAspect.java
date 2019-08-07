package com.yzyfdf.library.aop;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;

/**
 * Created by SJJ .
 * 描述 ${请求权限}
 */
@Aspect
public class PermissionAspect {
    private static final String TAG = "PermissionAspect";

    @Pointcut("execution(@com.yzyfdf.library.aop.PermissionsRequest * *(..)) && @annotation(permissionsRequest)")
    public void requestPermissions(PermissionsRequest permissionsRequest) {
    }

    @SuppressWarnings("CheckStyle")
    @Around("requestPermissions(permissionsRequest)")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint, PermissionsRequest permissionsRequest) throws Throwable {
        if (permissionsRequest == null) return;

        final Object object = joinPoint.getThis();
        if (object == null) return;

        RxPermissions rxPermissions;
        if (object instanceof FragmentActivity) {
            rxPermissions = new RxPermissions(((FragmentActivity) object));
        } else if (object instanceof Fragment) {
            rxPermissions = new RxPermissions(((Fragment) object));
        } else {
            throw new Exception("只支持Fragment和Activity");
        }

        rxPermissions.request(permissionsRequest.value())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtils.dTag(TAG, "权限处理完成");
                        if (aBoolean) {
                            try {
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                LogUtils.eTag(TAG, throwable.getMessage());
                            }
                        } else {
                            //拒绝后的操作
                            Toasty.info(Utils.getApp(), "拒绝了权限").show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.eTag(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * 检测权限有没有在清单文件中注册
     *
     * @param context            Context
     * @param requestPermissions 请求的权限组
     */
    static void checkPermissions(Context context, List<String> requestPermissions) throws Exception {
        List<String> manifestPermissions = getManifestPermissions(context);
        if (manifestPermissions != null && !manifestPermissions.isEmpty()) {
            for (String permission : requestPermissions) {
                if (!manifestPermissions.contains(permission)) {
                    throw new Exception(permission + "  没有在Manifest注册");
                }
            }
        } else {
            throw new Exception("Manifest中没有危险权限");
        }
    }

    /**
     * 返回应用程序在清单文件中注册的权限
     */
    static List<String> getManifestPermissions(Context context) {
        try {
            return Arrays.asList(context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions);
        } catch (PackageManager.NameNotFoundException ignored) {
            return null;
        }
    }

}
