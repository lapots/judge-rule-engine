package com.lapots.breed.judge.repository;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration for performance monitoring.
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class PerfMetricsConfiguration {
    /**
     * Monitoring pointcut.
     */
    @Pointcut("execution(* com.lapots.breed.judge.repository.*Repository.*(..))")
    public void monitor() {
    }

    /**
     * Creates instance of performance monitor interceptor.
     * @return performance monitor interceptor
     */
    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }

    /**
     * Creates instance of performance monitor advisor.
     * @return performance monitor advisor
     */
    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.lapots.breed.judge.repository.PerfMetricsConfiguration.monitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }
}
