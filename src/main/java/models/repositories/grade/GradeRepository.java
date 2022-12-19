package models.repositories.grade;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.grade.GradeService;
import models.services.student.StudentService;
import models.services.subject_group.SubjectGroupService;
import models.view_models.grade.GradeCreateRequest;
import models.view_models.grade.GradeUpdateRequest;
import models.view_models.grade.GradeViewModel;
import models.view_models.student.StudentViewModel;
import models.view_models.subject_group.SubjectGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GradeRepository implements IGradeRepository{
    private static GradeRepository instance = null;
    private final String tableName = "grade";
    public static GradeRepository getInstance(){
        if(instance == null)
            instance = new GradeRepository();
        return instance;
    }

    @Override
    public boolean insert(GradeCreateRequest request) {
        if(retrieveById(request.getStudentId(), request.getSubjectGroupId()) != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("studentId", request.getStudentId(), "subjectGroupId", request.getSubjectGroupId())
                    .withDouble("middleGrade", request.getMiddleGrade())
                    .withDouble("finalGrade", request.getFinalGrade())
                    .withDouble("totalGrade", request.getTotalGrade())
                    .withString("deleted", request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(GradeUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "middleGrade");
            expressionAttributeNames.put("#Q", "finalGrade");
            expressionAttributeNames.put("#R", "totalGrade");
            expressionAttributeNames.put("#S", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getMiddleGrade());
            expressionAttributeValues.put(":val2", request.getFinalGrade());
            expressionAttributeValues.put(":val3", request.getTotalGrade());
            expressionAttributeValues.put(":val4", request.getDeleted());

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("studentId", request.getStudentId(), "subjectGroupId", request.getSubjectGroupId())
                    .withUpdateExpression("set #P = :val1, #Q = :val2, #R = :val3, #S = :val4")
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

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("studentId", hashKey, "subjectGroupId", rangeKey)
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
    private GradeViewModel getGradeViewModel(String studentId, String subjectGroupId, double middleGrade, double finalGrade, double totalGrade, String deleted){
        GradeViewModel grade = new GradeViewModel();
        grade.setStudentId(studentId);
        grade.setSubjectGroupId(subjectGroupId);
        grade.setMiddleGrade(middleGrade);
        grade.setFinalGrade(finalGrade);
        grade.setDeleted(Integer.parseInt(deleted));
        grade.setTotalGrade(totalGrade);
        StudentViewModel student = StudentService.getInstance().retrieveById(studentId, "");
        grade.setStudentName(student.getStudentName());

        SubjectGroupViewModel subjectGroup = SubjectGroupService.getInstance().retrieveById(subjectGroupId, "");
        grade.setSubjectGroupName(subjectGroup.getSubjectGroupName());
        return grade;
    }
    @Override
    public GradeViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {
            item = table.getItem("studentId", hashKey, "subjectGroupId", rangeKey, "studentId, subjectGroupId, middleGrade, finalGrade, totalGrade, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getGradeViewModel(item.getString("studentId"),
                item.getString("subjectGroupId"), item.getDouble("middleGrade"), item.getDouble("finalGrade"),item.getDouble("totalGrade"), item.getString("deleted"));
    }

    @Override
    public ArrayList<GradeViewModel> retrieveAll() {
        ArrayList<GradeViewModel> grades = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("studentId", "subjectGroupId","middleGrade", "finalGrade","totalGrade", "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue studentId = item.getOrDefault("studentId", new AttributeValue());
                AttributeValue subjectGroupId = item.getOrDefault("subjectGroupId", new AttributeValue());
                AttributeValue middleGrade = item.getOrDefault("middleGrade", new AttributeValue());
                AttributeValue finalGrade = item.getOrDefault("finalGrade", new AttributeValue());
                AttributeValue totalGrade = item.getOrDefault("totalGrade", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                grades.add(getGradeViewModel(studentId.getS(), subjectGroupId.getS(), Double.parseDouble(middleGrade.getN()), Double.parseDouble(finalGrade.getN()),Double.parseDouble(totalGrade.getN()), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return grades;
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
    public boolean containStudent(String studentId) {
        return contain(studentId);
    }

    @Override
    public boolean containSubjectGroup(String subjectGroupId) {
        return contain(subjectGroupId);
    }

    @Override
    public ArrayList<GradeViewModel> retrieveGradeByLectureId(String lectureId) {
        ArrayList<SubjectGroupViewModel> subjectGroups = SubjectGroupService.getInstance().retrieveAll();
        subjectGroups.removeIf(x -> !Objects.equals(x.getLectureId(), lectureId));
        ArrayList<GradeViewModel> grades = GradeService.getInstance().retrieveAll();
        subjectGroups.forEach(x -> grades.removeIf(g -> !Objects.equals(g.getSubjectGroupId(), x.getSubjectGroupId())));

        return grades;
    }
}
