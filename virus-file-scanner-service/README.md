## Service Oriented Architecture - Microservices - Labs Project

### Virus File Scanner Service

To start the service and other dependency services run: `docker-compose up -d`

#### To test the virus file scanner service:

After starting the docker orchestration go to - http://localhost:9093 - S3 API, this will redirect you to the MinIO Console on - http://localhost:9098.
Use - minioadmin:minioadmin for username:password accordingly to login.
Create a bucket named 'fileuploadvirusscan' and upload some file. You will also need to add a Event Notification Trigger (for uploading files) in the bucket.
Here are steps to configure event (we have implemented a rest controller and used a webhook to test all the functionality).

![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/c79a7cfb-2d33-42dd-95ff-9a3402899b98)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/e287a689-018a-4806-ac38-15bae3c2d141)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/59f6f861-8388-4720-8494-4e375e1a1157)
After creating the Webhook event, it will ask you to restart MinIO for the appropriate changes to be applied.
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/36be5a8a-1d01-430a-ae4e-3c9ce36591e6)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/09dea707-578c-49a1-b1a0-bfb8cd75b0dc)

After this, upload a file to the bucket. (MinIO will ping the webhook on http://localhost:8088/webhook).

Then open: http://localhost:9000/topic/file-upload-topic/messages and http://localhost:9000/topic/corrupted-file-topic/messages to check the kafka message brokers.
Click on "View Messages" button and it will show the messages. You will have a message for every uploaded file into the specific 'fileuploadvirusscan' bucket.
