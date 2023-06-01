## File Upload Service Documentation
This is a file upload service built using FastAPI and MinIO. It allows users to upload files to a MinIO object storage server and download files from it. The service exposes the following endpoints:

### 1. Hello Endpoint
- **Endpoint:** `/hello`
- **HTTP Method:** `GET`
- **Description:** Returns a simple greeting message to verify that the service is up and running.
- **Response Example:**
    ``` json
    {
        "message": "Hello from file-upload-service!"
    }
### 2. File Upload Endpoint
- **Endpoint:** `/upload`
- **HTTP Method:** `POST`
- **Description:** Accepts a file upload and saves it to the configured MinIO object storage server.
- **Request Parameters:**
    - file (required): The file to be uploaded.
- **Response Examples:**
    - If the file is uploaded successfully:

        ``` json
        {
            "message": "File uploaded successfully"
        }    
    - If the file upload fails:
        ``` json
        {
            "message": "File upload failed: <error_message>"
        }
### 3. File Download Endpoint
- **Endpoint:** `/download/{filename}`
- **HTTP Method:** `GET`
- **Description:** Downloads the specified file from the configured MinIO object storage server.
- **Path Parameters:**
    - filename (required): The name of the file to be downloaded.
- **Response Examples:**
    - If the file is found and downloaded successfully:
        - The file will be streamed back to the client as the response with the appropriate content type.
    - If the file download fails:
        ```json
        {
            "error": "<error_message>"
        }
**Note:** 

Please note that this documentation assumes that you have set up and configured the MinIO object storage server and have the necessary environment variables (`minio_access_key`, `minio_secret_key`, and `minio_bucket`) properly set. Additionally, you may need to adjust the MinIO endpoint (`minio_endpoint`) according to your environment.

### **Diagrams**
#### 1. Sequence diagram
![sequence](https://github.com/stefankochev/soa-labs-project/assets/94232533/be2a7308-b7b5-43e9-ac5f-ef227cc05636)

#### 2. Use case diagram
![use_case](https://github.com/stefankochev/soa-labs-project/assets/94232533/2b49ee38-af14-4509-897b-3ec0ae2d05ab)



