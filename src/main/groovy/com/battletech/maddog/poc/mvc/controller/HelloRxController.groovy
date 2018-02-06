package com.battletech.maddog.poc.mvc.controller

import com.battletech.maddog.poc.mvc.facade.HelloRxFacade
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import io.reactivex.Observable

@RestController
@RequestMapping("/api/hello-rx")
class HelloRxController {

    final static Logger LOG = LoggerFactory.getLogger(HelloRxController)

    @Autowired
    HelloRxFacade helloRxFacade

    @RequestMapping(value = "/{name:.+}", method = RequestMethod.GET)
    DeferredResult<ResponseEntity> helloUser(@PathVariable String name){
        LOG.debug("In Rx controller...")
        toDeferredResult(helloRxFacade.doTaskRx(name))
    }

    private static toDeferredResult(Observable o){
        DeferredResult<ResponseEntity> deferred =  new DeferredResult<ResponseEntity>()

        o.subscribe(
                { m ->
                    deferred.setResult(new ResponseEntity(m, HttpStatus.OK))
                },
                { e ->
                    println e
                    deferred.setErrorResult(e)
                }
        )

        deferred
    }
}
