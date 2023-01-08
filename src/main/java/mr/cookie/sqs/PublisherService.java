package mr.cookie.sqs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService {

    private final SqsClient sqsClient;
    private final String queueUrl;
    private int counter;

    @Scheduled(fixedRateString = "${mr.cookie.publisher-fix-rate}", initialDelay = 2000)
    private void publishToSqs() {
        var response = sqsClient.sendMessage(builder -> builder
                .queueUrl(queueUrl)
                .messageBody("SQS Message %d".formatted(counter))
                .build());

        log.info("Messages for counter {} is stored with id: {}", counter, response.messageId());

        counter++;
    }

}
