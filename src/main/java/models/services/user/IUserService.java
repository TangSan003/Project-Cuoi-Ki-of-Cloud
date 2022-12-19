package models.services.user;

import models.view_models.user.UserCreateRequest;
import models.view_models.user.UserUpdateRequest;
import models.view_models.user.UserViewModel;

import java.util.ArrayList;

public interface IUserService {
    boolean insert(UserCreateRequest request);
    boolean update(UserUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    UserViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<UserViewModel> retrieveAll();
    boolean login(String username, String password);
}
