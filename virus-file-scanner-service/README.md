## Service Oriented Architecture - Microservices - Labs Project

### Virus File Scanner Service

To start the service and other dependency services run: `docker-compose up -d`

#### To test the virus file scanner service:

After starting the docker orchestration go to - http://localhost:9093 - S3 API, this will redirect you to the MinIO Console on - http://localhost:9098.
Use - minioadmin:minioadmin for username:password accordingly to login.
Create a bucket named 'fileuploadvirusscan' and upload some file. You will also need to add a Event Notification Trigger (for uploading files) in the bucket.
Here are steps to configure event (we have implemented a rest controller and used a webhook to test all the functionality).

After this, upload a file to the bucket. (MinIO will ping the webhook on http://localhost:8088/webhook).

Then open: http://localhost:9000/topic/file-upload-topic/messages and http://localhost:9000/topic/corrupted-file-topic/messages to check the kafka message brokers.
Click on "View Messages" button and it will show the messages. You will have a message for every uploaded file into the specific 'fileuploadvirusscan' bucket.
