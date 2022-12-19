package models.repositories.user_role;


import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.user_role.UserRoleCreateRequest;
import models.view_models.user_role.UserRoleUpdate;
import models.view_models.user_role.UserRoleViewModel;

public interface IUserRoleRepository extends IModifyEntity<UserRoleCreateRequest, UserRoleUpdate, String, String>,
        IRetrieveEntity<UserRoleViewModel, String, String> {
    boolean containRole(String roleId);
    boolean containUser(String username);
}
