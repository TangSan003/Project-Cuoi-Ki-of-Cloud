package models.repositories.user;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.user.UserCreateRequest;
import models.view_models.user.UserUpdateRequest;
import models.view_models.user.UserViewModel;

public interface IUserRepository extends IModifyEntity<UserCreateRequest, UserUpdateRequest, String, String>,
        IRetrieveEntity<UserViewModel, String, String> {
    boolean login(String username, String password);
}
