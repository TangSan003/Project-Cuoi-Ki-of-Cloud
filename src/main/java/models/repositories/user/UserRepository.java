package models.repositories.user;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.user_role.UserRoleService;
import models.view_models.user.UserViewModel;
import models.view_models.user.UserCreateRequest;
import models.view_models.user.UserUpdateRequest;
import utils.UserUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {
    private static UserRepository instance = null;
    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }
    private final String tableName = "user";
    @Override
    public boolean insert(UserCreateRequest request) {
        if(retrieveById(request.getUsername(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("username", request.getUsername())
                    .withString("password", UserUtils.hashPassword(request.getPassword()))
                    .withString("lectureId", request.getLectureId())
                    .withString("deleted", "0");
            table.putItem(item);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(UserUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P1", "lectureId");
            if(request.getPassword() != null && request.getPassword().length() > 0)
                expressionAttributeNames.put("#P2", "password");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getLectureId());
            String s = "set #P1 = :val1";
            if(request.getPassword() != null && request.getPassword().length() > 0) {
                expressionAttributeValues.put(":val2", UserUtils.hashPassword(request.getPassword()));
                s += ", #P2 = :val2";
            }

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("username", request.getUsername())
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
        if(UserRoleService.getInstance().containUser(hashKey))
            return false;
        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("username", hashKey)
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
    private UserViewModel getUserViewModel(String username, String password, String lectureId, String deleted){
        UserViewModel user = new UserViewModel();
        user.setUsername(username);
        user.setPassword(password);
        user.setLectureId(lectureId);
        user.setDeleted(Integer.parseInt(deleted));

        //LectureViewModel lecture = LectureService.getInstance().retrieveById(lectureId, "");
        //user.setLectureName(lecture.getLectureName());

        return user;
    }
    @Override
    public UserViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("username", hashKey, "username, password, lectureId, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getUserViewModel(item.getString("username"),
                item.getString("password"),
                item.getString("lectureId"),
                item.getString("deleted")
                );
    }

    @Override
    public ArrayList<UserViewModel> retrieveAll() {
        ArrayList<UserViewModel> users = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("username", "password", "lectureId", "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue username = item.getOrDefault("username", new AttributeValue());
                AttributeValue password = item.getOrDefault("password", new AttributeValue());
                AttributeValue lectureId = item.getOrDefault("lectureId", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                users.add(getUserViewModel(username.getS(), password.getS(), lectureId.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return users;
    }

    @Override
    public boolean login(String username, String password) {
        UserViewModel u = null;
        try {
            u = retrieveById(username, "");
        }
        catch(Exception e){
            return false;
        }
        if(u == null)
            return false;
        try {
            return u.getPassword().equals(UserUtils.hashPassword(password)) && u.getDeleted() != 1;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
