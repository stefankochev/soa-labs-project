from fastapi import FastAPI, File, UploadFile
from minio import Minio
from dotenv import load_dotenv
import os
from minio.error import InvalidResponseError, S3Error
from fastapi.responses import StreamingResponse

load_dotenv()

app = FastAPI()

# MinIO configuration
minio_endpoint = "localhost:9093"  # minio client endpoint
minio_access_key = os.environ.get('minio_access_key')
minio_secret_key = os.environ.get('minio_secret_key')
minio_bucket = os.environ.get('minio_bucket')

minio_client = Minio(
    minio_endpoint,
    access_key=minio_access_key,
    secret_key=minio_secret_key,
    secure=False
)

# buckets = minio_client.list_buckets()
# for bucket in buckets:
#     print(bucket.name, bucket.creation_date)

@app.get("/hello")
async def hello():
    return {"message": "Hello from file-upload-service!"}

@app.post("/upload")
async def upload_file(file: UploadFile = File(...)):
    try:
        # Save the file to MinIO
        minio_client.put_object(
            minio_bucket,  # Bucket name
            file.filename,  # Object name
            file.file,  # File stream
            file.content_type,  # Content type
        )
        # List objects in a bucket
        objects = minio_client.list_objects(minio_bucket)

        # Print the object names
        for obj in objects:
            print(obj.object_name)
        return {"message": "File uploaded successfully"}
    except InvalidResponseError as err:
        return {"message": f"File upload failed: {err}"}
    

@app.get("/download/{filename}")
async def download_file(filename: str):
    try:
        # Get file from MinIO
        data = minio_client.get_object(
            minio_bucket,
            filename
        )
        # Stream the file back to the client
        return StreamingResponse(data, media_type="application/octet-stream")
    except InvalidResponseError as err:
        return {"error": str(err)}
