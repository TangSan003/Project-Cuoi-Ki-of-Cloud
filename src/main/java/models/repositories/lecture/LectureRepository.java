package models.repositories.lecture;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.repositories.faculty.FacultyRepository;
import models.repositories.user.UserRepository;
import models.repositories.user_role.UserRoleRepository;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.AmazonS3.AmazonS3Service;
import models.services.subject_group.SubjectGroupService;
import models.view_models.faculty.FacultyViewModel;
import models.view_models.lecture.LectureViewModel;
import models.view_models.lecture.LectureCreateRequest;
import models.view_models.lecture.LectureUpdateRequest;
import models.view_models.user.UserViewModel;
import models.view_models.user_role.UserRoleViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LectureRepository implements ILectureRepository{
    private static LectureRepository instance = null;
    public static LectureRepository getInstance(){
        if(instance == null)
            instance = new LectureRepository();
        return instance;
    }
    private final String tableName = "lecture";
    @Override
    public boolean insert(LectureCreateRequest request) {
        if(retrieveById(request.getLectureId(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("lectureId", request.getLectureId())
                    .withString("facultyId",request.getFacultyId())
                    .withString("lectureName", request.getLectureName())
                    .withString("dob", request.getDob())
                    .withString("address", request.getAddress())
                    .withString("gender",request.getGender())
                    .withString("phone",request.getPhone())
                    .withString("image", AmazonS3Service.getInstance().uploadFile(request.getFile().getSubmittedFileName(), request.getFile().getInputStream()))
                    .withString("deleted",request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(LectureUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P1", "facultyId");
            expressionAttributeNames.put("#P2", "lectureName");
            expressionAttributeNames.put("#P3", "dob");
            expressionAttributeNames.put("#P4", "address");
            expressionAttributeNames.put("#P5", "gender");
            expressionAttributeNames.put("#P6", "phone");
            if(!Objects.equals(request.getFile().getSubmittedFileName(), ""))
                expressionAttributeNames.put("#P7", "image");
            expressionAttributeNames.put("#P8", "deleted");
            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getFacultyId());
            expressionAttributeValues.put(":val2", request.getLectureName());
            expressionAttributeValues.put(":val3", request.getDob());
            expressionAttributeValues.put(":val4", request.getAddress());
            expressionAttributeValues.put(":val5", request.getGender());
            expressionAttributeValues.put(":val6", request.getPhone());
            expressionAttributeValues.put(":val8", request.getDeleted());
            String s = "set #P1 = :val1, #P2 = :val2, #P3 = :val3, #P4 = :val4, #P5 = :val5, #P6 = :val6, #P8 = :val8";
            if(!Objects.equals(request.getFile().getSubmittedFileName(), "")) {
                expressionAttributeValues.put(":val7", AmazonS3Service.getInstance().uploadFile(request.getFile().getSubmittedFileName(), request.getFile().getInputStream()));
                s += ", #P7 = :val7";
            }

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("lectureId", request.getLectureId())
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
        if(SubjectGroupService.getInstance().containLecture(hashKey))
            return false;
        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("lectureId", hashKey)
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
    private LectureViewModel getLectureViewModel(String lectureId, String facultyId, String lectureName,
                                                 String dob, String address, String gender, String phone, String image, String deleted){
        LectureViewModel lecture = new LectureViewModel();

        lecture.setLectureId(lectureId);
        lecture.setLectureName(lectureName);
        lecture.setFacultyId(facultyId);
        lecture.setAddress(address);
        lecture.setGender(gender);
        lecture.setPhone(phone);
        lecture.setDob(dob);
        lecture.setDeleted(Integer.parseInt(deleted));
        FacultyViewModel faculty = FacultyRepository.getInstance().retrieveById(facultyId,"");
        lecture.setFacultyName(faculty.getFacultyName());

        ArrayList<UserViewModel> users = UserRepository.getInstance().retrieveAll();
        for(UserViewModel u:users){
            if(Objects.equals(u.getLectureId(), lectureId)){
                lecture.setUsername(u.getUsername());
                lecture.setPassword(u.getPassword());
                break;
            }
        }
        ArrayList<UserRoleViewModel> userRoles = UserRoleRepository.getInstance().retrieveAll();
        ArrayList<String> roleIds = new ArrayList<>();
        for(UserRoleViewModel u:userRoles){
            if(Objects.equals(u.getUsername(), lecture.getUsername())){
                roleIds.add(u.getRoleId());
            }
        }
        lecture.setImage(image);
        lecture.setRoleIds(roleIds);
        return lecture;
    }
    @Override
    public LectureViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("lectureId", hashKey, "lectureId, facultyId, lectureName, dob, address, gender, phone, image, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getLectureViewModel(item.getString("lectureId"),
                item.getString("facultyId"),
                item.getString("lectureName"),
                item.getString("dob"),
                item.getString("address"),
                item.getString("gender"),
                item.getString("phone"),
                item.getString("image"),
                item.getString("deleted"));
    }

    @Override
    public ArrayList<LectureViewModel> retrieveAll() {
        ArrayList<LectureViewModel> lectures = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("lectureId", "facultyId", "lectureName", "dob", "address", "gender", "phone", "image", "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){

                AttributeValue lectureId = item.getOrDefault("lectureId", new AttributeValue());
                AttributeValue facultyId = item.getOrDefault("facultyId", new AttributeValue());
                AttributeValue lectureName = item.getOrDefault("lectureName", new AttributeValue());
                AttributeValue dob = item.getOrDefault("dob", new AttributeValue());
                AttributeValue address = item.getOrDefault("address", new AttributeValue());
                AttributeValue gender = item.getOrDefault("gender", new AttributeValue());
                AttributeValue phone = item.getOrDefault("phone", new AttributeValue());
                AttributeValue image = item.getOrDefault("image", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());

                lectures.add(getLectureViewModel(lectureId.getS(), facultyId.getS(), lectureName.getS(), dob.getS()
                        , address.getS(), gender.getS(), phone.getS(), image.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return lectures;
    }

    @Override
    public boolean containLectureBelongFaculty(String facultyId) {
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("lectureId", "facultyId");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue id = item.getOrDefault("facultyId", new AttributeValue());
                if(Objects.equals(id.getS(), facultyId))
                    return true;
            }

        }catch(Exception e){
            return false;
        }
        return false;
    }
}
