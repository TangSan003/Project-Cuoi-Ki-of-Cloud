package models.repositories.user_role;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.services.role.RoleService;
import models.view_models.role.RoleViewModel;
import models.view_models.user_role.UserRoleCreateRequest;
import models.view_models.user_role.UserRoleUpdate;
import models.view_models.user_role.UserRoleViewModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class UserRoleRepository implements IUserRoleRepository{
    private static UserRoleRepository instance = null;
    public static UserRoleRepository getInstance(){
        if(instance == null)
            instance = new UserRoleRepository();
        return instance;
    }
    private final String tableName = "user_role";

    @Override
    public boolean insert(UserRoleCreateRequest request) {
        if(retrieveById(request.getUsername(), request.getRoleId()) != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("username", request.getUsername(), "roleId", request.getRoleId());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(UserRoleUpdate request) {
        return false;
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {
            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("username", hashKey, "roleId", rangeKey);

            DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    private UserRoleViewModel getUserRoleViewModel(String username, String roleId){
        UserRoleViewModel userRole = new UserRoleViewModel();
        userRole.setUsername(username);
        userRole.setRoleId(roleId);

        RoleViewModel role = RoleService.getInstance().retrieveById(roleId, "");
        userRole.setRoleName(role.getRoleName());
        return userRole;
    }
    @Override
    public UserRoleViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("username", hashKey, "roleId" ,rangeKey, "username, roleId", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getUserRoleViewModel(item.getString("username"),
                item.getString("roleId"));
    }

    @Override
    public ArrayList<UserRoleViewModel> retrieveAll() {
        ArrayList<UserRoleViewModel> userRoles = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("username", "roleId");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue username = item.getOrDefault("username", new AttributeValue());
                AttributeValue roleId = item.getOrDefault("roleId", new AttributeValue());
                userRoles.add(getUserRoleViewModel(username.getS(), roleId.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return userRoles;
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
    public boolean containRole(String roleId) {
        return contain(roleId);
    }

    @Override
    public boolean containUser(String username) {
        return contain(username);
    }
}
