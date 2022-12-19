package models.services.user_role;

import models.view_models.user_role.UserRoleCreateRequest;
import models.view_models.user_role.UserRoleUpdate;
import models.view_models.user_role.UserRoleViewModel;

import java.util.ArrayList;

public interface IUserRoleService {
    boolean insert(UserRoleCreateRequest request);
    boolean update(UserRoleUpdate request);
    boolean delete(String hashKey, String rangeKey);
    UserRoleViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<UserRoleViewModel> retrieveAll();
    boolean containRole(String roleId);
    boolean containUser(String username);
}
