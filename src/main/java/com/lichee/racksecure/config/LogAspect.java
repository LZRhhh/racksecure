package com.lichee.racksecure.config;



import com.lichee.racksecure.dao.LogDAO;
import com.lichee.racksecure.pojo.Log;
import com.lichee.racksecure.util.HttpContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogDAO logDAO;

    @Pointcut("@annotation(com.lichee.racksecure.config.SysLog)")
    public void pointcut() { }

    @Before("pointcut()")
    public void around(JoinPoint joinPoint) {
        // 保存日志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            // 注解上的描述
            log.setOperation(sysLog.value());
        }
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            log.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 模拟一个用户名
        log.setUsername("mrbird");
        log.setCreateTime(new Timestamp(new Date().getTime()));
        // 保存系统日志
        logDAO.save(log);
    }

}