package models.services.student_class;

import models.view_models.student_class.StudentClassCreateRequest;
import models.view_models.student_class.StudentClassUpdateRequest;
import models.view_models.student_class.StudentClassViewModel;

import java.util.ArrayList;

public interface IStudentClassService {
    boolean insert(StudentClassCreateRequest request);
    boolean update(StudentClassUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    StudentClassViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<StudentClassViewModel> retrieveAll();
    boolean containFaculty(String facultyId);

}
