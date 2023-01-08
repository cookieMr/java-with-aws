# Publisher/Consumer Java App with AWS SQS

This is a simple project to use SQS in some manner.

This project does one thing:
* publishes a message to an AWS SQS

### Running
The AWS SQS must exists prior to running this app and the proper credentials
need to be set in `~/.aws/credentials` file along with a region (for now
it's hardcoded to be `us-west-2`).

```bash
mvn clean install && \
mvn spring-boot:run
```

### AWS CLI - SQS
```bash
aws sqs get-queue-attributes \
  --region us-west-2 \
  --queue-url THE-QUEUE-URL \
  --attribute-names ApproximateNumberOfMessages
```
```bash
aws sqs purge-queue \
  --region us-west-2 \
  --queue-url THE-QUEUE-URL
```