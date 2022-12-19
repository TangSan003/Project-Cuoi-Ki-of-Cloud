package models.services.AmazonDynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public interface IAmazonDynamoDBService {
    DynamoDB getDynamoDB();
    AmazonDynamoDB getAmazonClient();
}
