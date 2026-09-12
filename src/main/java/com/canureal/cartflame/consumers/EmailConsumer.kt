package com.canureal.cartflame.consumers

import com.canureal.cartflame.dtos.EmailJobDto
import com.canureal.cartflame.services.EmailService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class EmailConsumer(
    private val emailService: EmailService,
) {
    @RabbitListener(queues = ["email-service"])
    fun handleEmailJob(job: EmailJobDto) {
        emailService.sendEmail(job.to,job.subject,job.body)
    }
}