package com.lind.xbootdemo.aop;

import com.lind.xbootdemo.annotation.SystemLog;
import com.lind.xbootdemo.entity.LogModel;
import com.lind.xbootdemo.service.LogService;
import com.lind.xbootdemo.utils.IpInfoUtil;
import com.lind.xbootdemo.utils.ObjectUtil;
import com.lind.xbootdemo.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Spring AOP实现日志管理.
 *
 * @author Exrickx
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
    @Autowired
    LogService logService;
    @Autowired(required = false)
    private HttpServletRequest request;
    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解.
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Map<String, Object> getControllerMethodInfo(JoinPoint joinPoint) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>(16);
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";
        Integer type = null;

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if (clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，原因是方法可以重载哦
                continue;
            }
            description = method.getAnnotation(SystemLog.class).description();
            type = method.getAnnotation(SystemLog.class).type().ordinal();
            map.put("description", description);
            map.put("type", type);
        }
        return map;
    }

    /**
     * Controller层切点,注解方式.
     */
    //@Pointcut("execution(* *..controller..*Controller*.*(..))")
    @Pointcut("@annotation(com.lind.xbootdemo.annotation.SystemLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间.
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {

        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
    }

    /**
     * 后置通知(在方法执行之后返回) 用于拦截Controller层操作.
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<GrantedAuthority> grantedAuthorities = Arrays.asList(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "read";
                }
            });
            UserDetails user = new User("test", "test", grantedAuthorities);
            if (authentication != null) {
                user = (UserDetails) authentication.getPrincipal();
            }
            String username = user.getUsername();
            LogModel log = new LogModel();

            //日志标题
            log.setName(getControllerMethodInfo(joinPoint).get("description").toString());
            //日志类型
            log.setLogType((int) getControllerMethodInfo(joinPoint).get("type"));
            //日志请求url
            log.setRequestUrl(request.getRequestURI());
            //请求方式
            log.setRequestType(request.getMethod());
            //请求参数
            Map<String, String[]> logParams = request.getParameterMap();
            log.setMapToParams(logParams);
            //请求用户
            log.setUsername(username);
            //请求IP
            log.setIp(ipInfoUtil.getIpAddr(request));
            //IP地址
            log.setIpInfo(ipInfoUtil.getIpCity(request));
            //请求开始时间
            Date logStartTime = beginTimeThreadLocal.get();

            long beginTime = beginTimeThreadLocal.get().getTime();
            long endTime = System.currentTimeMillis();
            //请求耗时
            Long logElapsedTime = endTime - beginTime;
            log.setCostTime(logElapsedTime.intValue());
            ipInfoUtil.getInfo(request, ObjectUtil.mapToStringAll(request.getParameterMap()));

            //调用线程保存至ES
            ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(log, logService));
        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
    }

    /**
     * 保存日志至数据库.
     */
    private static class SaveSystemLogThread implements Runnable {
        LogModel logModel;
        LogService logService;

        public SaveSystemLogThread(LogModel logModel, LogService logService) {
            this.logModel = logModel;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.save(logModel);
        }
    }

}
