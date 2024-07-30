package me.reporte.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.exchange.pending-proposal}")
    private String exchangePendingProposal;

    @Value("${rabbitmq.exchange.completed-proposal}")
    private String exchangeCompletedProposal;

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initRabbitAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    //region create exchange
    @Bean
    public FanoutExchange createExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange(this.exchangePendingProposal).build();
    }

    @Bean
    public FanoutExchange createExchangeCompletedProposal() {
        return ExchangeBuilder.fanoutExchange(this.exchangeCompletedProposal).build();
    }
    //endregion create exchange

    //region create queues
    @Bean
    public Queue createQueuePendingProposalMSNotification() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue createQueueCompletedProposalMSNotification() {
        return QueueBuilder.durable("completed-proposal.ms-notification").build();
    }
    //endregion create queues

    //region create bindings
    @Bean
    public Binding createBindingPendingProposeMSNotification() {
        return BindingBuilder.bind(createQueuePendingProposalMSNotification()).to(createExchangePendingProposal());
    }

    @Bean
    public Binding createBindingCompletedProposeMSNotification() {
        return BindingBuilder.bind(createQueueCompletedProposalMSNotification()).to(createExchangeCompletedProposal());
    }
    //endregion create bindings

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
