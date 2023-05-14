package com.zzj.mailparse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zzj"})
public class MailParseApplication {

    public static void main(String[] args) {

        SpringApplication.run(MailParseApplication.class, args);

    }

}
