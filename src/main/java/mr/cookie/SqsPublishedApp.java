package mr.cookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqsPublishedApp {
    public static void main(String[] args) {
        SpringApplication.run(SqsPublishedApp.class, args);
    }
}