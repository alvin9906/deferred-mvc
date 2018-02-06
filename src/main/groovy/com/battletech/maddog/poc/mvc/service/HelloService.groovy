package com.battletech.maddog.poc.mvc.service

import org.springframework.stereotype.Service

@Service
class HelloService {

    def doTask(String name){
        "Hello world $name"
    }
}
