package mr.cookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SqsPubSubApp {

    public static void main(String[] args) {
        SpringApplication.run(SqsPubSubApp.class, args);
    }

}