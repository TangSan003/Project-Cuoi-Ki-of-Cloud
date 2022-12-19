package models.repositories.subject;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.subject_group.SubjectGroupService;
import models.view_models.subject.SubjectViewModel;
import models.view_models.subject.SubjectCreateRequest;
import models.view_models.subject.SubjectUpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectRepository implements ISubjectRepository{
    private static SubjectRepository instance = null;
    public static SubjectRepository getInstance(){
        if(instance == null)
            instance = new SubjectRepository();
        return instance;
    }
    private final String tableName = "subject";
    @Override
    public boolean insert(SubjectCreateRequest request) {
        if(retrieveById(request.getSubjectId(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("subjectId", request.getSubjectId())
                    .withString("subjectName", request.getSubjectName())
                    .withString("creditsNo", String.valueOf(request.getCreditsNo()))
                    .withString("periodsNo", String.valueOf(request.getPeriodsNo()))
                    .withString("deleted", request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(SubjectUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P1", "subjectName");
            expressionAttributeNames.put("#P2", "creditsNo");
            expressionAttributeNames.put("#P3", "periodsNo");
            expressionAttributeNames.put("#P4", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getSubjectName());
            expressionAttributeValues.put(":val2", String.valueOf(request.getCreditsNo()));
            expressionAttributeValues.put(":val3", String.valueOf(request.getPeriodsNo()));
            expressionAttributeValues.put(":val4", request.getDeleted());

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("subjectId", request.getSubjectId())
                    .withUpdateExpression("set #P1 = :val1, #P2 = :val2, #P3 = :val3, #P4 = :val4")
                    .withNameMap(expressionAttributeNames)
                    .withValueMap(expressionAttributeValues);

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        if(SubjectGroupService.getInstance().containSubject(hashKey))
            return false;
        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("subjectId", hashKey)
                    .withUpdateExpression("set #P = :val1")
                    .withNameMap(expressionAttributeNames)
                    .withValueMap(expressionAttributeValues);

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    private SubjectViewModel getSubjectViewModel(String subjectId, String subjectName, String creditsNo, String periodsNo, String deleted){
        SubjectViewModel subject = new SubjectViewModel();
        subject.setSubjectId(subjectId);
        subject.setSubjectName(subjectName);
        subject.setCreditsNo(Integer.parseInt(creditsNo));
        subject.setPeriodsNo(Integer.parseInt(periodsNo));
        subject.setDeleted(Integer.parseInt(deleted));
        return subject;
    }
    @Override
    public SubjectViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("subjectId", hashKey, "subjectId, subjectName, creditsNo, periodsNo, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getSubjectViewModel(item.getString("subjectId"),
                item.getString("subjectName"),
                item.getString("creditsNo"),
                item.getString("periodsNo"),
                item.getString("deleted"));
    }

    @Override
    public ArrayList<SubjectViewModel> retrieveAll() {
        ArrayList<SubjectViewModel> subjects = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("subjectId", "subjectName", "creditsNo", "periodsNo", "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue subjectId = item.getOrDefault("subjectId", new AttributeValue());
                AttributeValue subjectName = item.getOrDefault("subjectName", new AttributeValue());
                AttributeValue creditsNo = item.getOrDefault("creditsNo", new AttributeValue());
                AttributeValue periodsNo = item.getOrDefault("periodsNo", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                subjects.add(getSubjectViewModel(subjectId.getS(), subjectName.getS(), creditsNo.getS(), periodsNo.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return subjects;
    }
}
