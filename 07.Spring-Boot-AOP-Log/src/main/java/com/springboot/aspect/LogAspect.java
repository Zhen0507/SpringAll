package com.springboot.aspect;
import com.springboot.annotation.Log;
import com.springboot.dao.SysLogDao;
import com.springboot.domain.SysLog;
import com.springboot.util.HttpContextUtils;
import com.springboot.util.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

/**
 * 系统日志切面处理类
 * 用于拦截带有@Log注解的方法，记录操作日志信息到数据库
 * 通过环绕通知实现方法执行时间统计和日志参数收集
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogDao sysLogDao;

    /**
     * 定义切入点：标记@Log注解的方法
     * 无参数无返回值，仅作为切入点标识
     */
    @Pointcut("@annotation(com.springboot.annotation.Log)")
    public void pointcut() {
    }

    /**
     * 环绕通知处理方法
     * @param point 连接点对象，提供被拦截方法的相关信息
     * 无返回值，通过异常捕获保证业务方法执行
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        long beginTime = System.currentTimeMillis();
        try {
            // 执行目标方法并继续后续处理
            point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 计算方法执行总耗时
        long time = System.currentTimeMillis() - beginTime;
        // 持久化日志记录
        saveLog(point, time);
        return true;
    }

    /**
     * 构建并保存系统日志记录
     * @param joinPoint 连接点对象，用于获取方法信息
     * @param time 方法执行耗时（毫秒）
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();

        // 从注解获取操作描述
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            sysLog.setOperation(logAnnotation.value());
        }

        // 构建完整方法签名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 处理请求参数信息
        Object[] args = joinPoint.getArgs();
        // 使用Java反射API获取参数名（需要添加-parameters编译参数）
        Parameter[] parameters = method.getParameters();
        
        if (args != null && parameters != null && args.length > 0) {
            StringBuilder params = new StringBuilder();
            // 拼接参数名和参数值
            for (int i = 0; i < args.length; i++) {
                params.append("  ")
                      .append(parameters[i].getName())
                      .append(": ")
                      .append(args[i]);
            }
            sysLog.setParams(params.toString());
        }

        // 获取客户端IP信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));

        // 设置基础日志信息
        sysLog.setUsername("mrbird"); // 模拟用户信息（实际项目应从会话获取）
        sysLog.setTime((int) time);
        sysLog.setCreateTime(new Date());

        // 持久化到数据库
        sysLogDao.saveSysLog(sysLog);
    }
}
