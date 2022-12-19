package models.services.role;

import models.view_models.role.RoleCreateRequest;
import models.view_models.role.RoleUpdateRequest;
import models.view_models.role.RoleViewModel;

import java.util.ArrayList;

public interface IRoleService {
    boolean insert(RoleCreateRequest request);
    boolean update(RoleUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    RoleViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<RoleViewModel> retrieveAll();
}
