package com.lichee.racksecure.interceptor;


import com.lichee.racksecure.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String contextPath=session.getServletContext().getContextPath();
        String[] userPages = new String[]{
                "user",
                "request"
        };
        String[] adminPages = new String[]{
                "admin"
        };
        String uri = httpServletRequest.getRequestURI();

        uri = StringUtils.delete(uri, contextPath+"/");
        String page = uri;
        if(beginWith(page, userPages)){

            User user = (User) session.getAttribute("user");

            if(user==null) {
                httpServletResponse.sendRedirect("/login");
                return false;
            }
        }
        if(beginWith(page, adminPages)){
            User user = (User) session.getAttribute("user");
            if(user==null || !user.getUsername().equals("admin")) {
                httpServletResponse.sendRedirect("/login?role=admin");
                return false;
            }
        }
        return true;
    }

    private boolean beginWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWithIgnoreCase(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
