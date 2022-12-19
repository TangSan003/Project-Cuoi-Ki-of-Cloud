package models.services.user_role;

import models.repositories.user_role.UserRoleRepository;
import models.view_models.user_role.UserRoleCreateRequest;
import models.view_models.user_role.UserRoleUpdate;
import models.view_models.user_role.UserRoleViewModel;

import java.util.ArrayList;

public class UserRoleService implements IUserRoleService{
    private static UserRoleService instance = null;
    public static UserRoleService getInstance(){
        if(instance == null)
            instance = new UserRoleService();
        return instance;
    }

    @Override
    public boolean insert(UserRoleCreateRequest request) {
        return UserRoleRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(UserRoleUpdate request) {
        return UserRoleRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return UserRoleRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public UserRoleViewModel retrieveById(String hashKey, String rangeKey) {
        return UserRoleRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<UserRoleViewModel> retrieveAll() {
        return UserRoleRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containRole(String roleId) {
        return UserRoleRepository.getInstance().containRole(roleId);
    }

    @Override
    public boolean containUser(String username) {
        return UserRoleRepository.getInstance().containUser(username);
    }
}
