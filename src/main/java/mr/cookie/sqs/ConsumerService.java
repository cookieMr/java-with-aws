package mr.cookie.sqs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageBatchRequestEntry;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final SqsClient sqsClient;
    private final String queueUrl;

    @Scheduled(fixedRateString = "${mr.cookie.consumer-fix-rate}", initialDelay = 1000)
    private void receiveMessages() {
        Set<DeleteMessageBatchRequestEntry> msgToDelete = new HashSet<>();

        sqsClient.receiveMessage(builder -> builder
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .build())
                .messages()
                .forEach(message -> {
                    log.info("Consuming message with counter {} and id {}.", message.body(), message.messageId());

                    msgToDelete.add(DeleteMessageBatchRequestEntry.builder()
                            .id(message.messageId())
                            .build());
                });

        sqsClient.deleteMessageBatch(builder -> builder.queueUrl(queueUrl)
                .entries(msgToDelete)
                .build());
    }

}
