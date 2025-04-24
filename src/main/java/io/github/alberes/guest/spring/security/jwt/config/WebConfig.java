package io.github.alberes.guest.spring.security.jwt.config;

import io.github.alberes.guest.spring.security.jwt.audit.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLog auditLog;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.auditLog)
                .addPathPatterns("/**");
                //.excludePathPatterns("/users/**");
    }
}
