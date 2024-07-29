package me.reporte.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"me.reporte.core.entity"})
// @EnableJpaRepositories({"me.reporte.core.repository"})
// @ComponentScan(basePackages = {"me.reporte.core", "me.reporte.notification"})
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

}
