package com.ansimtouchserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnsimTouchServerApplication

fun main(args: Array<String>) {
    runApplication<AnsimTouchServerApplication>(*args)
}
