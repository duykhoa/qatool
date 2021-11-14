package com.github.trambui.qatool

import com.github.javafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun getFaker(): Faker {
        return Faker();
    }
}