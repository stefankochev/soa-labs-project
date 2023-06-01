## Service Oriented Architecture - Microservices - Labs Project

## Project Structure
### Virus File Scanner Microservice

`virus-file-scanner-service` is a directory that includes several components that handle file uploads, virus scanning, and handling of corrupted files. They communicate with each other using Kafka as the messaging system.

* MinioFileUploadEventListener - this component handles the uploads, it is listening to new events triggered when a file is uploaded to MinIO. It retrieves the uploaded file, it creates an object and publishes a message in Kafka.

* FileVirusScanServiceImpl - this component handles the virus scanning. It receives the messages that contain the object which is then scanned for viruses and creates an object based on that scan result. Then that response is also published in Kafka

* CorruptedScannedFilesHandler - this component handles the corrupted files. It listens to the object in Kafka and process the response of the virus scan. If a file is marked as corrupted it removes the corresponding file from MinIO.

`docker-compose.yml`  defines the services and their configuration, including the Kong API Gateway and our services, minio, clamav, and virus-file-scanner-service which depends on minio, kafka and clamav services.

`README.md` - Markdown file that provides the documentation, instruction and information about the project.

### Running

`docker compose up`

Launches 7 docker containers
* kong: This container runs the Kong API gateway with ports `8000` for the public API and `8001` for the Admin API
* minio: This container runs the Minio object storage server on ports `9093` and `9098`
* clamav: This container runs the ClamAV antivirus service on port `3310`
* zookeeper: This container runs the ZooKeeper server, which is used by Kafka for coordination on port `2181`
* kafka: This container runs the Kafka message broker on port `9092`
* kafdrop: This container runs Kafdrop, a web UI for monitoring Kafka topics on port `9000`
* virus-file-scanner-service: This container runs the virus file scanner service on port `8088`


After this, upload a file to the bucket. MinIO will send appropriate WebHookPayload on the kafka topic ('test-topic') which you can check it on - http://localhost:9000/topic/test-topic/messages.

Then open: http://localhost:9000/topic/file-scan-topic/messages and http://localhost:9000/topic/corrupted-file-topic/messages to check the kafka message brokers.

### Accessing The Endpoints

After running the containers:  
* Admin API: You can access the Kong Admin API at `http://localhost:8001`.
* Proxy API: The Kong Proxy API can be accessed at `http://localhost:8000`.
* Minio Console: You can access the Minio console at `http://localhost:9098`.
* Minio Server: The Minio server is accessible at `http://localhost:9093`. 
* ClamAV TCP Port: ClamAV listens on port `3310` for TCP connections. However, since it is not exposed externally in the Docker Compose file, you won't be 	able to access it directly from your host machine. 
* ZooKeeper: accessible at `localhost:2181`.
* Kafka Broker: The Kafka broker is accessible at `localhost:9092`.
* Kafdrop: You can access the Kafdrop UI at `http://localhost:9000`.
* Virus File Scanner API: The Virus File Scanner Service API is accessible at `http://localhost:8088`.

### Testing

To test the functionality of the microservice create a bucket named 'fileuploadvirusscan'. Then you will also need to add a Event Notification Trigger (for uploading files) in the bucket.
Here are steps to configure event (we have set up a kafka event for the bucket to sent information on the dockerized kafka:9092 to a topic where the virus-file-scanner-service will listen).
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/c79a7cfb-2d33-42dd-95ff-9a3402899b98)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/5fde8e1b-8837-480a-9dc3-7d1ba297746f)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/ec27319b-2e94-4648-8b91-02cbde25cc0b)
After creating the Kafka Event in minio, it will ask you to restart MinIO for the appropriate changes to be applied.
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/36be5a8a-1d01-430a-ae4e-3c9ce36591e6)
![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/09dea707-578c-49a1-b1a0-bfb8cd75b0dc)

Then go to the 'fileuploadvirusscan' bucket and add the event that you have created. Upload some object to the bucket and go firstly to http://localhost:9000/topic/test-topic/messages to see that MinIO sent the Payload to kafka.
Then you can access the following topics appropriatly to check for the messages sent to topics 'file-scan-topic' and 'corrupted-file-topic' created by the virus-file-scanner-service - 
http://localhost:9000/topic/file-scan-topic/messages and http://localhost:9000/topic/corrupted-file-topic/messages.
If uploaded file has been detected by the ClamAV client that is infected, it will be removed from the MinIO.

Because in virus-file-scanner-service we used a logger, to log information about the functionalities, you can check the container logs by using the command: `docker container logs <CONTAINER_ID | CONTAINER_NAME>`.

### Diagram

![image](https://github.com/stefankochev/soa-labs-project/assets/61638603/7bffef37-7105-417a-a071-cc27b284356e)

