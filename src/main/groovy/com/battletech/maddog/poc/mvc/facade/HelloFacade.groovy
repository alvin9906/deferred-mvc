package com.battletech.maddog.poc.mvc.facade

import com.battletech.maddog.poc.mvc.service.HelloService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

import java.util.concurrent.CompletableFuture

@Component
class HelloFacade {

    final static Logger LOG = LoggerFactory.getLogger(HelloFacade)

    @Autowired
    HelloService helloService

    @Async
    CompletableFuture doTaskAsync(String name)throws InterruptedException {
        CompletableFuture.completedFuture(doTask(name))
    }

    def doTask(String name) {
        def result = ""
        try {
            LOG.debug("In Facade....")
            result = helloService.doTask(name)
            LOG.debug("Exiting Facade....")
        } catch (Exception e) {

        }
        result
    }
}
