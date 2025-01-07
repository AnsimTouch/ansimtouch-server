package com.ansimtouchserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
    org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration::class
])
class AnsimTouchServerApplication

fun main(args: Array<String>) {
    runApplication<AnsimTouchServerApplication>(*args)
}
