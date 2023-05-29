package finki.ukim.mk.virusfilescannerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class WebhookPayload {
    @JsonProperty("Records")
    private List<Record> records;

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
        private S3Details s3;

        // Getters and Setters for all the fields
    }

    public static class UserIdentity {
        @JsonProperty("principalId")
        private String principalId;

        // Getters and Setters
    }

    public static class RequestParameters {
        @JsonProperty("sourceIPAddress")
        private String sourceIPAddress;

        // Getters and Setters
    }

    public static class ResponseElements {
        @JsonProperty("x-amz-request-id")
        private String requestId;
        @JsonProperty("x-minio-deployment-id")
        private String deploymentId;

        // Getters and Setters
    }

    @Getter
    public static class S3Details {
        @JsonProperty("s3SchemaVersion")
        private String s3SchemaVersion;
        @JsonProperty("configurationId")
        private String configurationId;
        @JsonProperty("bucket")
        private BucketDetails bucket;
        @JsonProperty("object")
        private ObjectDetails object;

        // Getters and Setter
    }

    public static class BucketDetails {
        @JsonProperty("name")
        private String name;
        @JsonProperty("arn")
        private String arn;

        // Getters and Setters
    }

    @Getter
    public static class ObjectDetails {
        @JsonProperty("key")
        private String key;
        @JsonProperty("size")
        private long size;
        @JsonProperty("eTag")
        private String eTag;
        @JsonProperty("sequencer")
        private String sequencer;

        // Getters and Setters
    }
}