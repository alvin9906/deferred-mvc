package com.battletech.maddog.poc.mvc.facade

import com.battletech.maddog.poc.mvc.service.HelloService
import io.reactivex.Observable
import io.reactivex.Scheduler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HelloRxFacade {
    final static Logger LOG = LoggerFactory.getLogger(HelloRxFacade)

    @Autowired
    HelloService helloService

    @Autowired
    Scheduler scheduler

    Observable doTaskRx(String name)throws InterruptedException {
        return Observable.create{ s ->
            s.onNext(doTask(name))
            s.onComplete()
        }.subscribeOn(scheduler)
    }

    def doTask(String name) {
        def result = ""
        try {
            LOG.debug("In RxFacade....")
            result = helloService.doTask(name)
            LOG.debug("Exiting RxFacade....")
        } catch (Exception e) {

        }
        result
    }
}
