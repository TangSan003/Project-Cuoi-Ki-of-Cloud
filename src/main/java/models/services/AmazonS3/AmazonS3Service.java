package models.services.AmazonS3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;


import java.io.IOException;
import java.io.InputStream;

public class AmazonS3Service implements IAmazonS3Service {
    private static final String BUCKET = "studentms";
    private static final String url = "https://" + BUCKET + ".s3.amazonaws.com/";
    private static AmazonS3Service instance = null;
    public static AmazonS3Service getInstance(){
        if(instance == null)
            instance = new AmazonS3Service();
        return instance;
    }
    @Override
    public String uploadFile(String fileName, InputStream inputStream) {
        S3Client client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();
        try {
            client.putObject(request,
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
        } catch (IOException e) {
            return "";
        }
        S3Waiter waiter = client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();
        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
        return url + fileName;
    }
}
