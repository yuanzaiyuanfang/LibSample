//package com.yzyfdf.library.aop;
//
//import android.util.Log;
//
//import com.yzyfdf.library.base.BaseApplication;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.util.Random;
//
//import es.dmoral.toasty.Toasty;
//
///**
// * Created by SJJ .
// * 描述 ${登录拦截处理}
// */
//@Aspect
//public class LoginAspect {
//    private static final String TAG = "LoginAspect";
//
//    @Pointcut("execution(@com.yzyfdf.library.aop.LoginIntercept * *(..))")
//    public void loginFilter() {
//    }
//
//    @SuppressWarnings("CheckStyle")
//    @Around("loginFilter()")
//    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        //do something
//
//        Signature signature = joinPoint.getSignature();
//        if (!(signature instanceof MethodSignature)) {
//            throw new Exception("LoginIntercept注解只能用于方法上");
//        }
//
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Log.d("Aspect", String.valueOf(methodSignature.getName()));
//        Log.d("Aspect", String.valueOf(methodSignature.getMethod() == null));
//        LoginIntercept loginIntercept = methodSignature.getMethod().getAnnotation(LoginIntercept.class);
//        if (loginIntercept == null) {
//            return;
//        }
//
//        boolean login = new Random().nextInt(10) > 5;
//        if (login) {
//            joinPoint.proceed();
//        } else {
//            //去登录
//            Toasty.error(BaseApplication.getAppContext(),"还没登录").show();
//        }
//    }
//}
