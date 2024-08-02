package me.reporte.proposal.config;

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

    @Value("${rabbitmq.exchange.pending-proposal}")
    private String exchangePendingProposal;

    @Value("${rabbitmq.exchange.completed-proposal}")
    private String exchangeCompletedProposal;

    @Value("${rabbitmq.exchange.dlx.pending-proposal}")
    private String exchangeDlxPendingProposal;

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

    @Bean
    public FanoutExchange createDlxExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange(this.exchangeDlxPendingProposal).build();
    }
    //endregion create exchange

    //region create queues
    @Bean
    public Queue createQueuePendingProposalMSProposal() {
        return QueueBuilder.durable("pending-proposal.ms-proposal").build();
    }

    @Bean
    public Queue createQueueCompletedProposalMSProposal() {
        return QueueBuilder.durable("completed-proposal.ms-proposal").build();
    }

    @Bean
    public Queue createQueuePendingProposalDLQ() {
        return QueueBuilder.durable("pending-proposal.dlq").build();
    }
    //endregion create queues

    //region create bindings
    @Bean
    public Binding createBindingCompletedProposeMSProposal() {
        return BindingBuilder.bind(createQueueCompletedProposalMSProposal()).to(createExchangeCompletedProposal());
    }

    @Bean
    public Binding createBindingPendingProposeMSProposal() {
        return BindingBuilder.bind(createQueuePendingProposalMSProposal()).to(createExchangePendingProposal());
    }

    @Bean
    public Binding createBindingDLXPendingProposeMSProposal() {
        return BindingBuilder.bind(createQueuePendingProposalDLQ()).to(createDlxExchangePendingProposal());
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
