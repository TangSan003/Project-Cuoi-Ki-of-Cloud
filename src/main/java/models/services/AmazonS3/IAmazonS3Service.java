package models.services.AmazonS3;

import java.io.InputStream;

public interface IAmazonS3Service {
    String uploadFile(String fileName, InputStream inputStream);
}
