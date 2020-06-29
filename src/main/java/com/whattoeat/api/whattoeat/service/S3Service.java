package com.whattoeat.api.whattoeat.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class S3Service {

    private static final String DEFAULT_BUCKET_NAME = "what.to.eat";

    private static final String AWS_ACCESS_KEY = System.getenv("AWS_ACCESS_KEY");

    private static final String AWS_SECRET_KEY = System.getenv("AWS_SECRET_KEY");

    private static final AWSCredentials CREDENTIALS = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

    private static final AmazonS3 s3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS))
            .withRegion(Regions.US_EAST_2)
            .build();

    public String uploadFile(final byte[] file, final String path) {
        s3.putObject(
                new PutObjectRequest(
                        DEFAULT_BUCKET_NAME,
                        path,
                        new ByteArrayInputStream(file),
                        null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return "https://s3.us-east-2.amazonaws.com/" + DEFAULT_BUCKET_NAME + "/" + path;
    }

}
