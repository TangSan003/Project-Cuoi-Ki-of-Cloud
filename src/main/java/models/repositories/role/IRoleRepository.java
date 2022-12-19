package models.repositories.role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.role.RoleCreateRequest;
import models.view_models.role.RoleUpdateRequest;
import models.view_models.role.RoleViewModel;

public interface IRoleRepository extends IModifyEntity<RoleCreateRequest, RoleUpdateRequest, String, String>,
        IRetrieveEntity<RoleViewModel, String, String> {
}
