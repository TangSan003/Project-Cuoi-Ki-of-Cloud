package models.services.student;

import models.view_models.student.StudentCreateRequest;
import models.view_models.student.StudentUpdateRequest;
import models.view_models.student.StudentViewModel;

import java.util.ArrayList;

public interface IStudentService {
    boolean insert(StudentCreateRequest request);
    boolean update(StudentUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    StudentViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<StudentViewModel> retrieveAll();
    boolean containStudentClass(String studentClassId);
}
