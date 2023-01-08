package mr.cookie.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService {

    private final SqsClient sqsClient;

    @Value("${mr.cookie.queue-name:#{null}}")
    private String queueName;

    private int counter;
    private String queueUrl;

    @PostConstruct
    private void postConstruct() {
        log.info("Fetching SQS URL for [{}].", queueName);
        var getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(StringUtils.trimWhitespace(queueName))
                .build();
        queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
        log.info("Fetched SQS URL [{}].", queueUrl);
    }

    @Scheduled(fixedRateString = "${mr.cookie.publisher-fix-rate}", initialDelay = 2000)
    public void publishToSqs() {
        log.info("Sending message to SQS with counter {}.", counter);
        sqsClient.sendMessage(builder -> builder
                .queueUrl(queueUrl)
                .messageBody("SQS Message %d".formatted(counter++))
                .build());
    }

}
