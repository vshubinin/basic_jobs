package org.basic.jobs.admin;

import org.basic.jobs.ContextConfiguration;
import org.basic.jobs.admin.web.config.WebContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(
                new Object[]{Application.class, ContextConfiguration.class, WebContextConfiguration.class},
                args
        );
    }

}