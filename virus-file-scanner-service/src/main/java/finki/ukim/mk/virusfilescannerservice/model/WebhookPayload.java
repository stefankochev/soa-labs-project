package finki.ukim.mk.virusfilescannerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class WebhookPayload {

    @JsonProperty("EventName")
    private String eventName;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Records")
    private List<Record> records;

    //getters and setters for records
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Getter
    public static class Record {
        @JsonProperty("eventVersion")
        private String eventVersion;

        @JsonProperty("eventSource")
        private String eventSource;

        @JsonProperty("awsRegion")
        private String awsRegion;

        @JsonProperty("eventTime")
        private String eventTime;

        @JsonProperty("eventName")
        private String eventName;

        @JsonProperty("userIdentity")
        private UserIdentity userIdentity;

        @JsonProperty("requestParameters")
        private RequestParameters requestParameters;

        @JsonProperty("responseElements")
        private ResponseElements responseElements;

        @JsonProperty("s3")
        private S3 s3;

        @JsonProperty("source")
        private Source source;

        public static Record fromJson(String json) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Record.class);
        }
    }

    @Getter
    public static class UserIdentity {
        @JsonProperty("principalId")
        private String principalId;

    }

    @Getter
    public static class RequestParameters {
        @JsonProperty("principalId")
        private String principalId;

        @JsonProperty("region")
        private String region;

        @JsonProperty("sourceIPAddress")
        private String sourceIPAddress;

    }

    @Getter
    public static class ResponseElements {
        @JsonProperty("x-amz-id-2")
        private String amzId2;

        @JsonProperty("x-amz-request-id")
        private String amzRequestId;

        @JsonProperty("x-minio-deployment-id")
        private String minioDeploymentId;

        @JsonProperty("x-minio-origin-endpoint")
        private String minioOriginEndpoint;

    }

    @Getter
    public static class S3 {
        @JsonProperty("s3SchemaVersion")
        private String s3SchemaVersion;

        @JsonProperty("configurationId")
        private String configurationId;

        @JsonProperty("bucket")
        private Bucket bucket;

        @JsonProperty("object")
        private S3Object object;

    }

    @Getter
    public static class Bucket {
        @JsonProperty("name")
        private String name;

        @JsonProperty("ownerIdentity")
        private UserIdentity ownerIdentity;

        @JsonProperty("arn")
        private String arn;

    }

    @Getter
    public static class S3Object {
        @JsonProperty("key")
        private String key;

        @JsonProperty("size")
        private int size;

        @JsonProperty("eTag")
        private String eTag;

        @JsonProperty("contentType")
        private String contentType;

        @JsonProperty("userMetadata")
        private UserMetadata userMetadata;

        @JsonProperty("sequencer")
        private String sequencer;

    }

    @Getter
    public static class UserMetadata {
        @JsonProperty("content-type")
        private String contentType;

    }

    @Getter
    public static class Source {
        @JsonProperty("host")
        private String host;

        @JsonProperty("port")
        private String port;

        @JsonProperty("userAgent")
        private String userAgent;

    }
}