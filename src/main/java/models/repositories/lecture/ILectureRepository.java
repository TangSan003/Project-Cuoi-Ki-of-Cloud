package models.repositories.lecture;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.lecture.LectureCreateRequest;
import models.view_models.lecture.LectureUpdateRequest;
import models.view_models.lecture.LectureViewModel;

public interface ILectureRepository extends IModifyEntity<LectureCreateRequest, LectureUpdateRequest, String, String>,
        IRetrieveEntity<LectureViewModel, String, String> {
    boolean containLectureBelongFaculty(String facultyId);
}
