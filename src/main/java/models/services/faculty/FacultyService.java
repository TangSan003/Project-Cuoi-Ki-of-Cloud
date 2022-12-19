package models.services.faculty;

import models.repositories.faculty.FacultyRepository;
import models.view_models.faculty.FacultyCreateRequest;
import models.view_models.faculty.FacultyUpdateRequest;
import models.view_models.faculty.FacultyViewModel;

import java.util.ArrayList;

public class FacultyService implements IFacultyService{
    private static FacultyService instance = null;
    public static FacultyService getInstance(){
        if(instance == null)
            instance = new FacultyService();
        return instance;
    }
    @Override
    public boolean insert(FacultyCreateRequest request) {
        return FacultyRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(FacultyUpdateRequest request) {
        return FacultyRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return FacultyRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public FacultyViewModel retrieveById(String hashKey, String rangeKey) {
        return FacultyRepository.getInstance().retrieveById(hashKey,rangeKey);
    }

    @Override
    public ArrayList<FacultyViewModel> retrieveAll() {
        return FacultyRepository.getInstance().retrieveAll();
    }
}
