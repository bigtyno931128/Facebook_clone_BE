package com.best2team.facebook_clone_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class FacebookCloneBeApplication {

    @PostConstruct
    public void time() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(FacebookCloneBeApplication.class, args);

    }

//    public static final String APPLICATION_LOCATIONS = "spring.config.location="
//            + "classpath:/aws.yml"
//            +",classpath:/application.properties";
//
//    public static void main(String[] args) {
//        new SpringApplicationBuilder(FacebookCloneBeApplication.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
//    }

}

