## Service Oriented Architecture - Microservices - Labs Project

### Project Structure

`notification-service` is a  directory that contains the source code for receiving a notification when a file in posted on MinIO and needs to be scanned for viruses
`virus-file-scanner-service` is a directory that includes several components that handle file uploads, virus scanning, and handling of corrupted files. They communicate with each other using Kafka as the messaging system.
	  - MinioFileUploadEventListener - this component handles the uploads, it is listening to new events triggered when a file is uploaded to MinIO. It retrieves the uploaded file, it creates an object and publishes a message in Kafka.
	  - FileVirusScanServiceImpl - this component handles the virus scanning. It receives the messages that contain the object which is then scanned for viruses and creates an object based on that scan result. Then that response is also published in Kafka.
	  - CorruptedScannedFilesHandler - this component handles the corrupted files. It listens to the object in Kafka and process the response of the virus scan. If a file is marked as 
	corrupted it removes the corresponding file from MinIO.

`docker-compose.yml`  defines the services and their configuration, including the Kong API Gateway and our services, minio, clamav, and virus-file-scanner-service which depends on minio, kafka and clamav services.

`README.md` - Markdown file that provides the documentation, instruction and information about the project.

### Running

`docker compose up`

Launches 7 docker containers
	1. kong: This container runs the Kong API gateway with ports `8000` for the public API and `8001` for the Admin API
	2. minio: This container runs the Minio object storage server on ports `9093` and `9098`.
	3. clamav: This container runs the ClamAV antivirus service on port `3310`
	4. zookeeper: This container runs the ZooKeeper server, which is used by Kafka for coordination on port `2181`
	5. kafka: This container runs the Kafka message broker on port `9092`
	6. kafdrop: This container runs Kafdrop, a web UI for monitoring Kafka topics on port `9000`
	7. virus-file-scanner-service: This container runs the virus file scanner service on port `8088`


### Accessing The Endpoints

After running the containers:
1.Kong:
	- Admin API: You can access the Kong Admin API at `http://localhost:8001`.
	- Proxy API: The Kong Proxy API can be accessed at `http://localhost:8000`.
2.Minio:
	- Minio Console: You can access the Minio console at `http://localhost:9098`.
	- Minio Server: The Minio server is accessible at `http://localhost:9093`.
3.ClamAV:
	- ClamAV TCP Port: ClamAV listens on port `3310` for TCP connections. However, since it is not exposed externally in the Docker Compose file, you won't be able     to access it directly from your host machine.
4.ZooKeeper:
	- ZooKeeper: accessible at `localhost:2181`.
5.Kafka:
	- Kafka Broker: The Kafka broker is accessible at `localhost:9092`.
	- Kafdrop: You can access the Kafdrop UI at `http://localhost:9000`.
6.Virus File Scanner Service:
	- Virus File Scanner API: The Virus File Scanner Service API is accessible at `http://localhost:8088`.

### Testing

Within the virus-file-scanner-service a rest controller is created (WebHookMinioController) which is responsible for handling webhook events triggered by MinIO object uploads, it serves for testing and receiving notifications from MinIO when  a new file is uploaded.
