## Service Oriented Architecture - Microservices - Labs Project

### Virus File Scanner Service

To start the service and other dependency services run: `docker-compose up -d`

#### To test the virus file scanner service:

After starting the docker orchestration go to - http://localhost:9093 - S3 API, this will redirect you to the MinIO Console on - http://localhost:9098.
Use - minioadmin:minioadmin for username:password accordingly to login.
Create a bucket named 'fileuploadvirusscan' and upload some file. You will also need to add a Event Notification Trigger (for uploading files) in the bucket.
Here are steps to configure event (we have set up a kafka event for the bucket to sent information on the dockerized kafka:9092 to a topic where the virus-file-scanner-service will listen).

![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/c79a7cfb-2d33-42dd-95ff-9a3402899b98)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/5fde8e1b-8837-480a-9dc3-7d1ba297746f)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/ec27319b-2e94-4648-8b91-02cbde25cc0b)
After creating the Kafka Event in minio, it will ask you to restart MinIO for the appropriate changes to be applied.
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/36be5a8a-1d01-430a-ae4e-3c9ce36591e6)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/09dea707-578c-49a1-b1a0-bfb8cd75b0dc)

After this, upload a file to the bucket. MinIO will send appropriate WebHookPayload on the kafka topic ('test-topic') which you can check it on - http://localhost:9000/topic/test-topic/messages.

Then open: http://localhost:9000/topic/file-scan-topic/messages and http://localhost:9000/topic/corrupted-file-topic/messages to check the kafka message brokers.
Click on "View Messages" button and it will show the messages. You will have a message for every uploaded file into the specific 'fileuploadvirusscan' bucket.
