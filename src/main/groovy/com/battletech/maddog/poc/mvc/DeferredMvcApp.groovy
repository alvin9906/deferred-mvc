package com.battletech.maddog.poc.mvc

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.lang.reflect.Method
import java.util.concurrent.Executor

@Configuration
@EnableAutoConfiguration
@EnableAsync(proxyTargetClass = true)
@ComponentScan(['com.battletech.maddog.poc.mvc'])
@SpringBootApplication
class DeferredMvcApp implements AsyncConfigurer {

    @Override
    Executor getAsyncExecutor() {
        executor()
    }

    @Override
    AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        new AsyncUncaughtExceptionHandler() {
            @Override
            void handleUncaughtException(Throwable ex, Method method, Object... params) {
                // handle exception
            }
        }
    }

    @Bean
    Executor executor(){
        Executor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(50)
        executor.setQueueCapacity(20)
        executor.initialize()
        executor.setThreadNamePrefix("async-exec-")

        executor
    }

    @Bean
    Scheduler scheduler(){
        Schedulers.from(executor())
    }

    static void main(String[] args) {
        SpringApplication.run DeferredMvcApp, args
    }
}
