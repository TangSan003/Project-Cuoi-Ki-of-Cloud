package models.repositories.student;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.entities.StudentClass;
import models.view_models.student.StudentCreateRequest;
import models.view_models.student.StudentUpdateRequest;
import models.view_models.student.StudentViewModel;

public interface IStudentRepository extends IModifyEntity<StudentCreateRequest, StudentUpdateRequest, String, String>,
        IRetrieveEntity<StudentViewModel, String, String> {
    boolean containStudentClass(String studentClassId);
}
