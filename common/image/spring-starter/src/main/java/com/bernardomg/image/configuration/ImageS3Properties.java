package com.bernardomg.image.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("image.s3")
public class ImageS3Properties {

    private String  accessKey;

    private String  bucket ;

    private String  endpoint;

    private boolean pathStyle;

    private String  region ;

    private String  secretKey;
    
    public ImageS3Properties() {
        super();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getRegion() {
        return region;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public boolean isPathStyle() {
        return pathStyle;
    }

    public void setAccessKey(final String value) {
        accessKey = value;
    }

    public void setBucket(final String value) {
        bucket = value;
    }

    public void setEndpoint(final String value) {
        endpoint = value;
    }

    public void setPathStyle(final boolean value) {
        pathStyle = value;
    }

    public void setRegion(final String value) {
        region = value;
    }

    public void setSecretKey(final String value) {
        secretKey = value;
    }

}
