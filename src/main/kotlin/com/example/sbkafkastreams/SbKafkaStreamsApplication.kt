package com.example.sbkafkastreams

import com.github.javafaker.Faker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.MessageBuilder
import java.util.function.Consumer
import java.util.function.Supplier


@SpringBootApplication
class SbKafkaStreamsApplication {
    @Bean
    fun produceChuckNorris(): Supplier<Message<String>> {
        return Supplier {
            MessageBuilder.withPayload(Faker.instance().chuckNorris().fact()).build()
        }
    }
//    @Bean
//    fun consumeChuckNorris(): Consumer<Message<String>>{
//        println("EEEE")
//        return Consumer { s:Message<String>->
//            println("DDDDD")
////            println("FACT: \u001B[3m = $s \u001B[0m")
//        }
//    }

}

fun main(args: Array<String>) {
    runApplication<SbKafkaStreamsApplication>(*args)
}

