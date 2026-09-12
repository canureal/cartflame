package com.canureal.cartflame.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {
    @Bean
    fun emailQueue(): Queue = Queue("email.queue", true)

    @Bean
    fun emailExchange(): TopicExchange = TopicExchange("email.exchange")

    @Bean
    fun emailBinding(emailQueue: Queue, emailExchange: TopicExchange): Binding =
        BindingBuilder.bind(emailQueue).to(emailExchange).with("email.send")

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}