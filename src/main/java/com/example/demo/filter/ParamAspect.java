package com.example.demo.filter;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Aspect
@Component
public class ParamAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.example.demo.filter.ParamFilter)")
    public void dataScopePointCut() {
    }

    /**
     * 通知类型
     * @param point
     * @throws Throwable
     */
    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }


    /**
     * 处理
     * @param joinPoint  AOP的封装接口类：可以百度搜索 Aop joinPoint详解
     */
    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        ParamFilter paramFilter = getAnnotation(joinPoint);
        if (paramFilter == null)
            return;


        // 获取当前的用户
//        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();
//        if (currentUser != null) {
//            // 如果是超级管理员，则不过滤数据
//            if (!ROLE_ADMIN.equals(currentUser.getRole().getName())) {
//
//            }
//        }

        dataScopeFilter(joinPoint);
    }


    /**
     *
     */
    public void dataScopeFilter(JoinPoint joinPoint) {
        //ProceedingJoinPoint是joinPoint子接口
        ProceedingJoinPoint pjp = (ProceedingJoinPoint)joinPoint;
        //获取输入的参数列表
        Object[] args = pjp.getArgs();

        //TODO 这段也没太理解透
        RowFilterParam rowFilterParam;
        for (Object arg : args) {
            if (arg instanceof RowFilterParam){
                rowFilterParam = (RowFilterParam) arg;
                rowFilterParam.setData("123123213213"); //放入要传递到方法内的值
            }
        }
    }

    /**
     * 获取数据权限注解
     * @param joinPoint
     * @return 注解
     */
    private ParamFilter getAnnotation(JoinPoint joinPoint) {
        //Signature用于跟踪或记录应用程序以获取有关连接点的反射信息
        Signature signature = joinPoint.getSignature();

        //方法签名类 MethodSignature 依赖于Signature
        MethodSignature methodSignature = (MethodSignature) signature;

        //获取被增强的方法本身Method这个类，通过该类执行反射操作
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(ParamFilter.class);
        }
        return null;
    }

}
