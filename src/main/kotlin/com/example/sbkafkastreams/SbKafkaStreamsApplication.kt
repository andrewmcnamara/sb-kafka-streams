package com.example.sbkafkastreams

import com.github.javafaker.Faker
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.util.function.Consumer
import java.util.function.Supplier


class Request(val query: String, val contentType: String)
class StatusResult(val statusCode: Int, val statusText: String)
class Response(val statusResult: StatusResult){
    operator fun invoke(statusResult: StatusResult.()->Unit){
        
    }
}
class RouteHandler(val request: Request, val response:Response) {
    fun response(value: () -> Unit) {

    }
}

fun get(path: String, func: RouteHandler.()->Unit) = func

fun main(args: Array<String>) {
    val xx =get("/hello") {
        response { statusCode = 405 }
    }
    xx(RouteHandler(Request(),Response())
}



@SpringBootApplication
class SbKafkaStreamsApplication {
    @Bean
    fun produceChuckNorris(): Supplier<Message<String>> {
        return Supplier {
            MessageBuilder.withPayload(Faker.instance().chuckNorris().fact()).build()
        }
    }

    @Bean
    fun consumeChuckNorris(): Consumer<Message<String>> {
        println("EEEE")
        return Consumer { s: Message<String> ->
//            println("DDDDD")
            println("FACT: \u001B[3m «" + s.payload + "\u001B[0m»")
        }
    }

//    @Bean
//    fun processWords(): Function<KStream<String?, String?>?, KStream<String?, Long?>?>? {
//        return Function<KStream<String, String>, KStream<String, Long>> { inputStream ->
//            val stringSerde: Serde<String> = String()
//            val countsStream: KStream<String?, Long?> = inputStream
//                .flatMapValues { value -> asList(value.toLowerCase().split("\\W+")) }
//                .map { key, value -> KeyValue(value, value) }
//                .groupByKey(Grouped.with(stringSerde, stringSerde))
//                .count(`as`("word-count-state-store"))
//                .toStream()
//            countsStream.to("counts", Produced.with(stringSerde, Long()))
//            countsStream
//        }
//    }

    @Bean
    fun processWords(): java.util.function.Function<KStream<String?, String>, KStream<String, Long>> {
        return java.util.function.Function { inputStream: KStream<String?, String> ->
            val countStream = inputStream
                .flatMapValues { value: String -> value.toLowerCase().split("\\W+".toRegex()) }
                .map { _: String?, value: String -> KeyValue(value, value) }
                .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.`as`("word-count-state-store"))
                .toStream()
            countStream.to("counts", Produced.with(Serdes.String(), Serdes.Long()))
            countStream
        }
    }

}

fun main(args: Array<String>) {
    runApplication<SbKafkaStreamsApplication>(*args)
}

