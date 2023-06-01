## Service Oriented Architecture - Microservices - Labs Project

### Project Structure

`notification-service` is a  directory that contains the source code for receiving a notification when a file in posted on MinIO and needs to be scanned for viruses__
`virus-file-scanner-service` is a directory that includes several components that handle file uploads, virus scanning, and handling of corrupted files. They communicate with each other using Kafka as the messaging system.__
	  - MinioFileUploadEventListener - this component handles the uploads, it is listening to new events triggered when a file is uploaded to MinIO. It retrieves the uploaded file, it creates an object and publishes a message in Kafka.__
	  - FileVirusScanServiceImpl - this component handles the virus scanning. It receives the messages that contain the object which is then scanned for viruses and creates an object based on that scan result. Then that response is also published in Kafka.__
	  - CorruptedScannedFilesHandler - this component handles the corrupted files. It listens to the object in Kafka and process the response of the virus scan. If a file is marked as corrupted it removes the corresponding file from MinIO.__

`docker-compose.yml`  defines the services and their configuration, including the Kong API Gateway and our services, minio, clamav, and virus-file-scanner-service which depends on minio, kafka and clamav services.__

`README.md` - Markdown file that provides the documentation, instruction and information about the project.__

### Running

`docker compose up`__

Launches 7 docker containers__
	1. kong: This container runs the Kong API gateway with ports `8000` for the public API and `8001` for the Admin API__
	2. minio: This container runs the Minio object storage server on ports `9093` and `9098`.__
	3. clamav: This container runs the ClamAV antivirus service on port `3310`__
	4. zookeeper: This container runs the ZooKeeper server, which is used by Kafka for coordination on port `2181`__
	5. kafka: This container runs the Kafka message broker on port `9092`__
	6. kafdrop: This container runs Kafdrop, a web UI for monitoring Kafka topics on port `9000`__
	7. virus-file-scanner-service: This container runs the virus file scanner service on port `8088`__


### Accessing The Endpoints

After running the containers:__
1.Kong:__
	- Admin API: You can access the Kong Admin API at `http://localhost:8001`.__
	- Proxy API: The Kong Proxy API can be accessed at `http://localhost:8000`.__
2.Minio:__
	- Minio Console: You can access the Minio console at `http://localhost:9098`.__
	- Minio Server: The Minio server is accessible at `http://localhost:9093`.__
3.ClamAV:__
	- ClamAV TCP Port: ClamAV listens on port `3310` for TCP connections. However, since it is not exposed externally in the Docker Compose file, you won't be 	able to access it directly from your host machine.__
4.ZooKeeper:__
	- ZooKeeper: accessible at `localhost:2181`.__
5.Kafka:__
	- Kafka Broker: The Kafka broker is accessible at `localhost:9092`.__
	- Kafdrop: You can access the Kafdrop UI at `http://localhost:9000`.__
6.Virus File Scanner Service:__
	- Virus File Scanner API: The Virus File Scanner Service API is accessible at `http://localhost:8088`.__

### Testing

Within the virus-file-scanner-service a rest controller is created (WebHookMinioController) which is responsible for handling webhook events triggered by MinIO object uploads, it serves for testing and receiving notifications from MinIO when  a new file is uploaded.__
