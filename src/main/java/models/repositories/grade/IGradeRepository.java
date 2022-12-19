package models.repositories.grade;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.grade.GradeCreateRequest;
import models.view_models.grade.GradeUpdateRequest;
import models.view_models.grade.GradeViewModel;

import java.util.ArrayList;

public interface IGradeRepository extends IModifyEntity<GradeCreateRequest, GradeUpdateRequest, String, String>,
        IRetrieveEntity<GradeViewModel, String, String> {
    boolean containStudent(String studentId);
    boolean containSubjectGroup(String subjectGroupId);

    ArrayList<GradeViewModel> retrieveGradeByLectureId(String lectureId);
}
