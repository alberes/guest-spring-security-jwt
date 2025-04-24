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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuditLog implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuditLog.class);

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
        Object principal = authentication.getPrincipal();
        HttpServletRequest httpServletRequest = this.getServletRequest();
        if(principal instanceof  UserPrincipal userPrincipal) {
            authentication.getPrincipal();
            String authorization = httpServletRequest.getHeader("Authorization");
            logger.info("Request - Method: {}, URI: {}, Username: {}, Toke: {}",
                    httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                    userPrincipal.getUsername(), authorization);
        }else{
            logger.info("Request - Method: {}, URI: {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("PreHandle");
        this.audit();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        logger.info("PostHandle");
        this.audit();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if(response.getStatus() >= 300){
            logger.error("Error service: {}", response.getStatus());
        }else {
            logger.info("AfterCompletion Status: {}", response.getStatus());
        }
        this.audit();
    }
}
