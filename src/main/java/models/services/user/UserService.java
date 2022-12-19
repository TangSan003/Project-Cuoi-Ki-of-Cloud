package models.services.user;

import models.repositories.user.UserRepository;
import models.view_models.user.UserCreateRequest;
import models.view_models.user.UserUpdateRequest;
import models.view_models.user.UserViewModel;

import java.util.ArrayList;

public class UserService implements IUserService{
    private static UserService instance = null;
    public static UserService getInstance(){
        if(instance == null)
            instance = new UserService();
        return instance;
    }

    @Override
    public boolean insert(UserCreateRequest request) {
        return UserRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(UserUpdateRequest request) {
        return UserRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return UserRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public UserViewModel retrieveById(String hashKey, String rangeKey) {
        return UserRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<UserViewModel> retrieveAll() {
        return UserRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean login(String username, String password) {
        return UserRepository.getInstance().login(username, password);
    }
}
