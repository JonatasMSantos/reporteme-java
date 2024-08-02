package me.reporte.proposal;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EntityScan({"me.reporte.core.entity"})
@EnableJpaRepositories({"me.reporte.core.repository"})
@ComponentScan(basePackages = {"me.reporte.core", "me.reporte.proposal"})
@EnableWebSocketMessageBroker
public class ProposalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProposalApplication.class, args);
	}

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
