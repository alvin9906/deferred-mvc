package org.maddog.mvc.controller

import org.maddog.mvc.facade.HelloFacade
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

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

@RestController
@RequestMapping("/api/hello")
class HelloController {

    final static Logger LOG = LoggerFactory.getLogger(HelloController)

    @Autowired
    HelloFacade helloFacade

    @RequestMapping(value = "/{name:.+}", method = RequestMethod.GET)
    DeferredResult<ResponseEntity> helloUser(@PathVariable String name){
        LOG.debug("In controller...")
        toDeferredResult(helloFacade.doTaskAsync(name))
    }

    private static toDeferredResult(CompletableFuture f) {
        DeferredResult<ResponseEntity> deferred = new DeferredResult<>()

        f.whenComplete{ res, ex ->
            if (ex !=null){
                if (ex instanceof CompletionException){
                    deferred.setErrorResult(ex.cause)
                } else {
                    deferred.setErrorResult(ex)
                }
            } else {
                deferred.setResult(new ResponseEntity(res, HttpStatus.OK))
            }
        }

        deferred
    }
}
