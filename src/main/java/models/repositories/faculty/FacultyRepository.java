package models.repositories.faculty;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.repositories.lecture.LectureRepository;
import models.repositories.student_class.StudentClassRepository;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.AmazonS3.AmazonS3Service;
import models.view_models.faculty.FacultyCreateRequest;
import models.view_models.faculty.FacultyViewModel;
import models.view_models.faculty.FacultyUpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FacultyRepository implements IFacultyRepository{

    private static FacultyRepository instance = null;
    public static FacultyRepository getInstance(){
        if(instance == null)
            instance = new FacultyRepository();
        return instance;
    }
    private final String tableName = "faculty";
    @Override
    public boolean insert(FacultyCreateRequest request) {
        if(retrieveById(request.getFacultyId(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("facultyId", request.getFacultyId())
                    .withString("facultyName", request.getFacultyName())
                    .withString("image", AmazonS3Service.getInstance().uploadFile(request.getFile().getSubmittedFileName(),
                            request.getFile().getInputStream()))
                    .withString("deleted", request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(FacultyUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "facultyName");
            if(!Objects.equals(request.getFile().getSubmittedFileName(), ""))
                expressionAttributeNames.put("#Q", "image");

            expressionAttributeNames.put("#R", "deleted");
            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getFacultyName());
            expressionAttributeValues.put(":val3", request.getDeleted());
            String s = "set #P = :val1, #R = :val3";
            if(!Objects.equals(request.getFile().getSubmittedFileName(), "")) {
                expressionAttributeValues.put(":val2", AmazonS3Service.getInstance().uploadFile(request.getFile().getSubmittedFileName(),
                        request.getFile().getInputStream()));
                s += ", #Q = :val2";
            }

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("facultyId", request.getFacultyId())
                    .withUpdateExpression(s)
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
        if(LectureRepository.getInstance().containLectureBelongFaculty(hashKey) ||
                StudentClassRepository.getInstance().containFaculty(hashKey))
            return false;
        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("facultyId", hashKey)
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
    private FacultyViewModel getFacultyViewModel(String facultyId, String facultyName, String image, String deleted){
        FacultyViewModel faculty = new FacultyViewModel();
        faculty.setFacultyId(facultyId);
        faculty.setFacultyName(facultyName);
        faculty.setDeleted(Integer.parseInt(deleted));
        faculty.setImage(image);
        return faculty;
    }
    @Override
    public FacultyViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("facultyId", hashKey, "facultyId, facultyName, image, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getFacultyViewModel(item.getString("facultyId"),
                item.getString("facultyName"),
                item.getString("image"),
                item.getString("deleted"));
    }

    @Override
    public ArrayList<FacultyViewModel> retrieveAll() {
        ArrayList<FacultyViewModel> faculties = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName)
                    .withAttributesToGet("facultyId", "facultyName", "image" , "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue facultyId = item.getOrDefault("facultyId", new AttributeValue());
                AttributeValue facultyName = item.getOrDefault("facultyName", new AttributeValue());
                AttributeValue image = item.getOrDefault("image", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                faculties.add(getFacultyViewModel(facultyId.getS(), facultyName.getS(),image.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return faculties;
    }
}
