package com.OSMaster.transportlayer.s3;

import com.OSMaster.config.exception.GenericException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AwsBucket {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public AwsBucket(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadCsv(String content, String fileName) {
        try {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType("text/csv")
                    .build();

            s3Client.putObject(putReq, RequestBody.fromBytes(bytes));

            return fileName;
        } catch (Exception e) {
            throw new GenericException("Erro ao fazer upload do CSV para o S3: " + e.getMessage());
        }
    }

    public List<String> listFilesByPrefix(String prefix) {
        ListObjectsV2Request listReq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();

        return s3Client.listObjectsV2(listReq).contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public String getFileContent(String key) {
        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (ResponseInputStream<?> s3is = s3Client.getObject(getReq);
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new GenericException("Erro ao ler arquivo do S3: " + key);
        }
    }
}