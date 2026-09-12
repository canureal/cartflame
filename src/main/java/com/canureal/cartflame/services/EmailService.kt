package com.canureal.cartflame.services

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
) {
    public fun sendEmail(to: String, subject: String, body: String) {
        val msg = SimpleMailMessage()
        msg.setTo(to)
        msg.subject = subject
        msg.text = body
        mailSender.send(msg)
    }
}