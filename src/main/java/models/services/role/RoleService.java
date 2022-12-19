package models.services.role;


import models.entities.Role;
import models.repositories.role.RoleRepository;
import models.view_models.role.RoleCreateRequest;
import models.view_models.role.RoleUpdateRequest;
import models.view_models.role.RoleViewModel;

import java.util.ArrayList;

public class RoleService implements IRoleService{
    private static RoleService instance = null;
    public static RoleService getInstance(){
        if(instance == null)
            instance = new RoleService();
        return instance;
    }

    @Override
    public boolean insert(RoleCreateRequest request) {
        return RoleRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(RoleUpdateRequest request) {
        return RoleRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return RoleRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public RoleViewModel retrieveById(String hashKey, String rangeKey) {
        return RoleRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<RoleViewModel> retrieveAll() {
        return RoleRepository.getInstance().retrieveAll();
    }
}
