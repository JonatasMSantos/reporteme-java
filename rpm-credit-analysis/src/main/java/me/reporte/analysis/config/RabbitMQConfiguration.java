package me.reporte.analysis.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.pendingProposal.exchange}")
    private String exchange;

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initRabbitAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    //region create queues
    @Bean
    public Queue createQueuePendingProposalMSCreditAnalysis() {
        return QueueBuilder.durable("pending-proposal.ms-credit-analysis").build();
    }

    @Bean
    public Queue createQueuePendingProposalMSNotification() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue createQueueCompletedMSProposal() {
        return QueueBuilder.durable("completed-proposal.ms-proposal").build();
    }

    @Bean
    public Queue createQueueCompletedProposalMSNotification() {
        return QueueBuilder.durable("completed-proposal.ms-notification").build();
    }
    //endregion create queues

    //region create exchange
    @Bean
    public FanoutExchange createExchangeProposePendingMSCreditAnalysis() {
        return ExchangeBuilder.fanoutExchange(this.exchange).build();
    }
    //endregion create exchange

    //region create bindings
    @Bean
    public Binding createBindingProposePendingMSCreditAnalysis() {
        return BindingBuilder.bind(createQueuePendingProposalMSCreditAnalysis()).to(createExchangeProposePendingMSCreditAnalysis());
    }

    @Bean
    public Binding createBindingProposePendingMSNotification() {
        return BindingBuilder.bind(createQueuePendingProposalMSNotification()).to(createExchangeProposePendingMSCreditAnalysis());
    }
    //endregion create bindings

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
