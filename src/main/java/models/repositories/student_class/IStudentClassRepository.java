package models.repositories.student_class;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.student_class.StudentClassCreateRequest;
import models.view_models.student_class.StudentClassUpdateRequest;
import models.view_models.student_class.StudentClassViewModel;

public interface IStudentClassRepository extends IModifyEntity<StudentClassCreateRequest, StudentClassUpdateRequest, String, String>,
        IRetrieveEntity<StudentClassViewModel, String, String> {
    boolean containFaculty(String facultyId);
}
