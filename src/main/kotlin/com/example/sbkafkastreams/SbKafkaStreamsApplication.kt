package com.example.sbkafkastreams

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SbKafkaStreamsApplication

fun main(args: Array<String>) {
	runApplication<SbKafkaStreamsApplication>(*args)
}
