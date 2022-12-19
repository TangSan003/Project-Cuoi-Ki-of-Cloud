package models.repositories.faculty;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.faculty.FacultyCreateRequest;
import models.view_models.faculty.FacultyViewModel;
import models.view_models.faculty.FacultyUpdateRequest;

public interface IFacultyRepository extends IModifyEntity<FacultyCreateRequest, FacultyUpdateRequest, String, String>,
        IRetrieveEntity<FacultyViewModel, String, String> {

}
