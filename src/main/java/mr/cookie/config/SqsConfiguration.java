package mr.cookie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class SqsConfiguration {

    @Value("${mr.cookie.queue-name:#{null}}")
    private String queueName;

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @Bean
    public String queueUrl(SqsClient sqsClient) {
        var getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(StringUtils.trimWhitespace(queueName))
                .build();
        return sqsClient.getQueueUrl(getQueueRequest).queueUrl();
    }

    @PreDestroy
    private void preDestroy() {
        log.info("Closing SQS connection.");
        sqsClient().close();
    }

}
