package models.services.faculty;

import models.view_models.faculty.FacultyCreateRequest;
import models.view_models.faculty.FacultyUpdateRequest;
import models.view_models.faculty.FacultyViewModel;

import java.util.ArrayList;

public interface IFacultyService {
    public boolean insert(FacultyCreateRequest request);
    public boolean update(FacultyUpdateRequest request);
    public boolean delete(String hashKey, String rangeKey);
    public FacultyViewModel retrieveById(String hashKey, String rangeKey);
    public ArrayList<FacultyViewModel> retrieveAll();
}
