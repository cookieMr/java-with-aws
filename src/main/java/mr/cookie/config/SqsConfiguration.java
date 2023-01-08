package mr.cookie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class SqsConfiguration {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @PreDestroy
    private void preDestroy() {
        log.info("Closing SQS connection.");
        sqsClient().close();
    }

}
