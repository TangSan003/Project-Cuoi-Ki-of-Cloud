package models.repositories.subject_group;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.lecture.LectureService;
import models.services.subject.SubjectService;
import models.view_models.lecture.LectureViewModel;
import models.view_models.subject.SubjectViewModel;
import models.view_models.subject_group.SubjectGroupCreateRequest;
import models.view_models.subject_group.SubjectGroupUpdateRequest;
import models.view_models.subject_group.SubjectGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SubjectGroupRepository implements ISubjectGroupRepository{
    private static SubjectGroupRepository instance = null;
    public static SubjectGroupRepository getInstance(){
        if(instance == null)
            instance = new SubjectGroupRepository();
        return instance;
    }
    private final String tableName = "subject_group";
    @Override
    public boolean insert(SubjectGroupCreateRequest request) {
        if(retrieveById(request.getSubjectGroupId(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("subjectGroupId", request.getSubjectGroupId())
                    .withString("subjectGroupName", request.getSubjectGroupName())
                    .withString("subjectId", request.getSubjectId())
                    .withString("lectureId", request.getLectureId())
                    .withString("deleted", request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(SubjectGroupUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P1", "subjectGroupName");
            expressionAttributeNames.put("#P2", "subjectId");
            expressionAttributeNames.put("#P3", "lectureId");
            expressionAttributeNames.put("#P4", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getSubjectGroupName());
            expressionAttributeValues.put(":val2", request.getSubjectId());
            expressionAttributeValues.put(":val3", request.getLectureId());
            expressionAttributeValues.put(":val4", request.getDeleted());

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("subjectGroupId", request.getSubjectGroupId())
                    .withUpdateExpression("set #P1 = :val1,#P2 = :val2,#P3 = :val3, #P4 = :val4 ")
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

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("subjectGroupId", hashKey)
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
    private SubjectGroupViewModel getSubjectGroupViewModel(String subjectGroupId, String subjectGroupName, String subjectId, String lectureId, String deleted){
        SubjectGroupViewModel subjectGroup = new SubjectGroupViewModel();
        subjectGroup.setSubjectGroupId(subjectGroupId);
        subjectGroup.setSubjectGroupName(subjectGroupName);
        subjectGroup.setSubjectId(subjectId);
        subjectGroup.setLectureId(lectureId);
        subjectGroup.setDeleted(Integer.parseInt(deleted));

        SubjectViewModel subject = SubjectService.getInstance().retrieveById(subjectId, "");
        subjectGroup.setSubjectName(subject.getSubjectName());
        LectureViewModel lecture = LectureService.getInstance().retrieveById(lectureId, "");
        subjectGroup.setLectureName(lecture.getLectureName());

        return subjectGroup;
    }
    @Override
    public SubjectGroupViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("subjectGroupId", hashKey, "subjectGroupId, subjectGroupName, subjectId, lectureId, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getSubjectGroupViewModel(item.getString("subjectGroupId"),
                item.getString("subjectGroupName"),
                item.getString("subjectId"),
                item.getString("lectureId"),
                item.getString("deleted")
                );
    }

    @Override
    public ArrayList<SubjectGroupViewModel> retrieveAll() {

        ArrayList<SubjectGroupViewModel> subjectGroups = new ArrayList<>();

        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("subjectGroupId", "subjectGroupName","subjectId" ,"lectureId" , "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue subjectGroupId = item.getOrDefault("subjectGroupId", new AttributeValue());
                AttributeValue subjectGroupName = item.getOrDefault("subjectGroupName", new AttributeValue());
                AttributeValue subjectId = item.getOrDefault("subjectId", new AttributeValue());
                AttributeValue lectureId = item.getOrDefault("lectureId", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                subjectGroups.add(getSubjectGroupViewModel(subjectGroupId.getS(), subjectGroupName.getS(),subjectId.getS(),lectureId.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return subjectGroups;
    }
    private boolean contain(String hashKey){
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet(hashKey);

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue id = item.getOrDefault(hashKey, new AttributeValue());
                if(Objects.equals(id.getS(), hashKey))
                    return true;
            }

        }catch(Exception e){
            return false;
        }
        return false;
    }
    @Override
    public boolean containLecture(String lectureId) {
        return contain(lectureId);
    }

    @Override
    public boolean containSubject(String subjectId) {
        return contain(subjectId);
    }

    @Override
    public ArrayList<SubjectGroupViewModel> retrieveSubjectGroupByLectureId(String lectureId) {
        ArrayList<SubjectGroupViewModel> subjectGroups = retrieveAll();
        subjectGroups.removeIf(x -> !Objects.equals(x.getLectureId(), lectureId));
        return subjectGroups;
    }
}
