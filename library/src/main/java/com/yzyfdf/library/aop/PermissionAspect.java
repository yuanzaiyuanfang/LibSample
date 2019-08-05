package com.yzyfdf.library.aop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

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
            return;
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

}
