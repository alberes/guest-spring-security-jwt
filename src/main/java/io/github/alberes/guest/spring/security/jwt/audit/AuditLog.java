package io.github.alberes.guest.spring.security.jwt.audit;

import io.github.alberes.guest.spring.security.jwt.domains.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuditLog implements HandlerInterceptor {

    private HttpServletRequest getServletRequest(){
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null){
            return null;
        }
        return servletRequestAttributes.getRequest();
    }

    public void audit(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        HttpServletRequest httpServletRequest = this.getServletRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        System.out.println("Username: " + userPrincipal.getUsername() + " token: " + authorization);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PreHandle");
        this.audit();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("PostHandle");
        this.audit();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if(response.getStatus() >= 300){
            System.out.println("Error service: " + response.getStatus());
        }
        System.out.println("AfterCompletion Status: " + response.getStatus());
        this.audit();
    }
}
