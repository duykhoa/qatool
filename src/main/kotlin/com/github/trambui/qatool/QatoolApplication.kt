package com.github.trambui.qatool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class QatoolApplication

fun main(args: Array<String>) {
	runApplication<QatoolApplication>(*args)
}