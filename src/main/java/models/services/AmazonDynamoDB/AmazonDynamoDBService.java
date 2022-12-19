package models.services.AmazonDynamoDB;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class AmazonDynamoDBService implements IAmazonDynamoDBService {
    private static AmazonDynamoDBService instance = null;
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .build();

    private static DynamoDB dynamoDB;
    public static AmazonDynamoDBService getInstance(){
        if(instance == null){
            instance = new AmazonDynamoDBService();
        }
        return instance;

    }
    @Override
    public DynamoDB getDynamoDB() {
        if(dynamoDB == null)
            dynamoDB = new DynamoDB(client);
        return dynamoDB;
    }

    @Override
    public AmazonDynamoDB getAmazonClient() {
        return client;
    }
}
