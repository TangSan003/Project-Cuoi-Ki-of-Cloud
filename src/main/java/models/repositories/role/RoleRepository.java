package models.repositories.role;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import models.repositories.user_role.UserRoleRepository;
import models.services.AmazonDynamoDB.AmazonDynamoDBService;
import models.view_models.role.RoleViewModel;
import models.view_models.role.RoleCreateRequest;
import models.view_models.role.RoleUpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoleRepository implements IRoleRepository{
    private static RoleRepository instance = null;
    public static RoleRepository getInstance(){
        if(instance == null)
            instance = new RoleRepository();
        return instance;
    }

    private final String tableName = "role";
    @Override
    public boolean insert(RoleCreateRequest request) {
        if(retrieveById(request.getRoleId(), "") != null)
            return false;
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        try {

            Item item = new Item().withPrimaryKey("roleId", request.getRoleId())
                    .withString("roleName", request.getRoleName())
                    .withString("deleted", request.getDeleted());
            table.putItem(item);

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(RoleUpdateRequest request) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);

        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "roleName");
            expressionAttributeNames.put("#Q", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", request.getRoleName());
            expressionAttributeValues.put(":val2", request.getDeleted());

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("roleId", request.getRoleId())
                    .withUpdateExpression("set #P = :val1, #Q = :val2")
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
        if(UserRoleRepository.getInstance().containRole(hashKey))
            return false;
        try {
            Map<String, String> expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#P", "deleted");

            Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
            expressionAttributeValues.put(":val1", "1");

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("roleId", hashKey)
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
    private RoleViewModel getRoleViewModel(String roleId, String roleName, String deleted){
        RoleViewModel role = new RoleViewModel();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setDeleted(Integer.parseInt(deleted));
        return role;
    }
    @Override
    public RoleViewModel retrieveById(String hashKey, String rangeKey) {
        Table table = AmazonDynamoDBService.getInstance().getDynamoDB().getTable(tableName);
        Item item = null;
        try {

            item = table.getItem("roleId", hashKey, "roleId, roleName, deleted", null);

        }
        catch (Exception e) {
            return null;
        }
        if(item == null)
            return null;
        return getRoleViewModel(item.getString("roleId"),
                item.getString("roleName"),
                item.getString("deleted"));
    }

    @Override
    public ArrayList<RoleViewModel> retrieveAll() {
        ArrayList<RoleViewModel> roles = new ArrayList<>();
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName)
                    .withAttributesToGet("roleId", "roleName", "deleted");

            ScanResult result = AmazonDynamoDBService.getInstance().getAmazonClient().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                AttributeValue roleId = item.getOrDefault("roleId", new AttributeValue());
                AttributeValue roleName = item.getOrDefault("roleName", new AttributeValue());
                AttributeValue deleted = item.getOrDefault("deleted", new AttributeValue());
                roles.add(getRoleViewModel(roleId.getS(), roleName.getS(), deleted.getS()));
            }

        }catch(Exception e){
            return null;
        }
        return roles;
    }
}
